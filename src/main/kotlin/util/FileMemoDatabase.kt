package util

import model.Category
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
            if (instanceCache[file.name] == null) {
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

            /* with(file.bufferedWriter()) {
                 write(jsonString)
                 close()
             }*/

            file.bufferedWriter().use {
                it.write(jsonString)
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

            /* with(file.bufferedReader()) {
                 val jsonString = readText()
                 close()*/
            file.bufferedReader().use {
                val jsonString = it.readText()

                val memos = MoshiParser.jsonAdapter.fromJson(jsonString)
                return memos ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override fun addMemo(memo: Memo): Boolean {
        val currentMemo = readMemo().toMutableList()
        currentMemo.add(memo)
        return writeMemo(currentMemo)
    }

    override fun modifyMemo(id: Int, content: String, category: Category): Boolean {
        val currentMemo = readMemo().toMutableList()

        val isValidId = (id > 0 && id <= currentMemo.size)
        val isEmptyContent = content.isEmpty()

        when {
            isValidId -> throw IllegalStateException("유효하지 않은 id 입니다")
            isEmptyContent -> throw IllegalArgumentException("내용이 비어 있습니다.")

            else -> {
                try {
                    val index = id - 1
                    when (currentMemo[index].id == id) {
                        true -> {
                            currentMemo.removeAt(index)
                            currentMemo.add(index, Memo(id, content, category)) //기존 순서 유지하면서 리스트 추가됨
                            return writeMemo(currentMemo)
                        }

                        false -> throw IllegalStateException("입력한 메모값이 index와 일치하지 않습니다")
                    }

                } catch (e: IndexOutOfBoundsException) {
                    throw IllegalArgumentException("유효하지 않는 id ${id}")
                }
            }
        }
    }


    override fun deleteMemo(id: Int): Boolean {
        try {
            val currentMemo = readMemo().toMutableList()
            if (currentMemo.isEmpty() || id - 1 < 0 || id - 1 >= currentMemo.size) {
                return false
            }
            currentMemo.removeAt(id - 1)
            writeMemo(currentMemo)
            return true

        } catch (e: Exception) {
            println("Bills 삭제 중 오류 발생: ${e.message}")
            return false
        }
    }


}