package util

import model.Memo
import java.io.File

class FileMemoDatabase private constructor() : IMemoDatabase {
    private var file: File = File(MEMO_FILENAME)

    companion object {
        const val MEMO_FILENAME = "memo_file.txt"

        @JvmStatic
        private var INSTANCE: FileMemoDatabase? = null

        fun getInstance(): FileMemoDatabase {
            if (INSTANCE == null) {
                INSTANCE = FileMemoDatabase()
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