package viewmodel

import model.Memo

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

    var memos: List<Memo> = mutableListOf()

    fun fetchMemos() {

    }

    fun addMemos() {

    }

    fun deleteMemos() {

    }

    fun modifyMemos() {

    }
}

class NavigationHandler() {

}
