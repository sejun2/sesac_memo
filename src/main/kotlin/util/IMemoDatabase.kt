package util

import model.Category
import model.Memo

interface IMemoDatabase {
    /// write memo list to database
    fun writeMemo(memos: List<Memo>): Boolean
    /// read memo list from database
    fun readMemo(): List<Memo>
    // add memo list to database
    fun addMemo(memo: Memo) : Boolean
    // modify list from database
    fun modifyMemo(id:Int, content: String, category: Category) : Boolean

    //delete list from database
    fun deleteMemo(id: Int): Boolean

    //delete all the list from database
    //fun deleteAllMemo(memos:List<Memo>) : Boolean

}