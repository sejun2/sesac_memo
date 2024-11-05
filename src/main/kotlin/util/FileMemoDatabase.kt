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

    /**
     * 조건 해당하는 index 찾지 못하면 ->  if (index == -1) return@runCatching false:  false 반환하고 runCatching 빠져나움
     * 찾으면 -> true 반환하고 메모 저장
     */
    override fun modifyMemo(memo: Memo): Boolean = runCatching {
        //1. id 유효성 검사
        require(memo.id > 0){"잘못된 id 입니다."}
        //2. 현재 메모 목록 가져오기
        val currentMemo = readMemo().toMutableList()
        //3. 수정할 메모의 인덱스 찾기
        val index = currentMemo.indexOfFirst{ it.id == memo.id } //currnentMemo에서 조건에 만족하는 첫번째 인덱스 찾음


        when{  //4. 조건에 만족하는 index 찾지 못하면  -1 반환  -> index == -1 이면 false 반환하고 runCatching 빠져나옴
            index == -1 -> return@runCatching false
            else -> {
                currentMemo[index] = memo
                writeMemo(currentMemo)
            }
        }

    }.getOrDefault(false)//예외가 발생했을 때 예외를 무시하고 디폴트값 받음


    override fun deleteMemo(id: Int): Boolean = runCatching {
        require(id > 0) { "잘못된 id 입니다." }

        val currentMemo = readMemo().toMutableList()
        val index = currentMemo.indexOfFirst { it.id == id }

        when{
            currentMemo.isEmpty() -> return@runCatching false
            index == -1 -> return@runCatching false
            else -> {
                currentMemo.removeAt(index)
                writeMemo(currentMemo)
            }
        }
    }.getOrDefault(false)

}