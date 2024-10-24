package util

import model.Memo
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.io.IOException

/**
 * File 로 관리되는 MemoDatabase Helper class
 * 메모 파일은 JSON string 형태로 저장된다
 * 읽기의 경우 List<[Memo]>,
 * 쓰기의 경우 [Memo] 데이터 클래스의 Serialized JSON String 형태로 저장된다
 */
class FileMemoDatabase private constructor(private val file: File) : IMemoDatabase {

    /**
     * instanceCache : dataBase 싱글턴 객체
     */
    companion object {
        private const val MEMO_FILENAME = "memo_file.txt"

        @Volatile
        private var instanceCache = HashMap<String, FileMemoDatabase>()

        @JvmStatic
        @Synchronized
        fun getInstance(file: File = File(MEMO_FILENAME)): FileMemoDatabase {
            if (instanceCache[file.name]== null) {
                instanceCache[file.name] = FileMemoDatabase(file)
            }
            return instanceCache.get(file.name)!!
        }

        @JvmStatic
        @TestOnly
        fun disposeInstance(name: String) {
            instanceCache.remove(name)
        }
    }

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override fun writeMemo(memos: List<Memo>): Boolean {
        try {
            val jsonString = MoshiParser.jsonAdapter.toJson(memos)
            if (!file.exists()) throw IOException()

            with(file.bufferedWriter()) {
                write(jsonString)
                close()
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


    override fun readMemo(): List<Memo> {
        try {
            if (!file.exists()) throw IOException()

            with(file.bufferedReader()) {
                val jsonString = readText()
                close()

                val memos = MoshiParser.jsonAdapter.fromJson(jsonString)
                return memos ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override fun addMemo(memo: Memo): Boolean {
        val currentMemo =  readMemo().toMutableList()
        currentMemo.add(memo)
        return writeMemo(currentMemo)
    }

    override fun modifyMemo(id:Int, content: String): Boolean {
        val currentMemo = readMemo().toMutableList()
        currentMemo.removeAt(id-1)
        currentMemo.add(Memo(id, content))
        return writeMemo(currentMemo)
    }


    override fun  deleteMemo(id: Int): Boolean {
        val currentMemo = readMemo().toMutableList()
        currentMemo.removeAt(id-1)
        return writeMemo(currentMemo)
    }


}