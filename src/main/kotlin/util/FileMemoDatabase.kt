package util

import model.Memo
import org.jetbrains.annotations.TestOnly
import java.io.File

/**
 * File 로 관리되는 MemoDatabase Helper class
 * 메모 파일은 JSON string 형태로 저장된다
 * 읽기의 경우 List<[Memo]>,
 * 쓰기의 경우 [Memo] 데이터 클래스의 Serialized JSON String 형태로 저장된다
 */
class FileMemoDatabase private constructor(private val file: File) : IMemoDatabase {

    companion object {
        private const val MEMO_FILENAME = "memo_file.txt"

        @JvmStatic
        @Volatile
        private var INSTANCE: FileMemoDatabase? = null

        @JvmStatic
        fun getInstance(file: File = File(MEMO_FILENAME)): FileMemoDatabase {
            if ((INSTANCE?.file?.name ?: "") != file.name) {
                INSTANCE = FileMemoDatabase(file)
            }

            if (INSTANCE == null) {
                INSTANCE = FileMemoDatabase(file)
            }

            return INSTANCE!!
        }
    }

    override fun writeMemo(memos: List<Memo>): Boolean {
        try {
            val jsonString = MoshiParser.jsonAdapter.toJson(memos)
            with(file.writer()) {
                write(jsonString)
                close()
            }

            println(file.absoluteFile)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun readMemo(): List<Memo> {
        try {
            with(file.reader()) {
                val jsonString = readText()
                close()

                val memos = MoshiParser.jsonAdapter.fromJson(jsonString)
                return memos ?: emptyList()
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}