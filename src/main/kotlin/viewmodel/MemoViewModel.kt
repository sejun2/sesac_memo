package viewmodel

import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase

class MemoViewModel private constructor() {

    companion object {
        @Volatile
        var INSTANCE: MemoViewModel? = null

        @JvmStatic
        @Synchronized
        fun getInstance(): MemoViewModel {
            if (INSTANCE == null) {
                INSTANCE = MemoViewModel()
            }

            return INSTANCE!!
        }
    }

    private var memos: List<Memo> = mutableListOf()
    private val fileDemo: IMemoDatabase = FileMemoDatabase.getInstance()

    fun fetchMemos(): List<Memo> {
        memos = fileDemo.readMemo()
        return memos
    }

    fun addMemos(memo: Memo) {
        fileDemo.addMemo(memo)
    }

    fun deleteMemos(id: Int) {
        fileDemo.deleteMemo(id)
    }

    fun modifyMemos(memo: Memo) {
        fileDemo.modifyMemo(memo)
    }

    fun getMemoById (id: Int): Memo? {
        val memoList = fetchMemos()
        val selectedMemo = memoList.find { it.id == id }
        return selectedMemo
    }

}


