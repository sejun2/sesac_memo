package util

import model.Memo

interface IMemoDatabase {
    /// write memo list to file
    fun writeMemo(memos: List<Memo>): Boolean

    /// read memo list
    fun readMemo(): List<Memo>
}