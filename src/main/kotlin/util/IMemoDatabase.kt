package util

import model.Memo

interface IMemoDatabase {
    fun writeMemo(memos: List<Memo>): Boolean
    fun readMemo() : List<Memo>
}