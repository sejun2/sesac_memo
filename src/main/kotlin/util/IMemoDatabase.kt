package util

import model.Memo

interface IMemoDatabase {
    /// write memo list to database
    fun writeMemo(memos: List<Memo>): Boolean

    /// read memo list from database
    fun readMemo(): List<Memo>
}