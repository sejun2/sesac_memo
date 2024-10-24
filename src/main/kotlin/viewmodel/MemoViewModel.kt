package viewmodel

import model.Memo
import screen.BaseMemoScreen
import screen.HomeScreen

class MemoViewModel private constructor() {
    lateinit var uiState: BaseMemoScreen

    companion object {
        @Volatile
        var INSTANCE: MemoViewModel? = null

        @JvmStatic
        @Synchronized
        fun getInstance(): MemoViewModel {
            if (INSTANCE == null) {
                INSTANCE = MemoViewModel()
            }
            INSTANCE!!.initializeUiState()
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

    private fun initializeUiState() {
        uiState = HomeScreen(this)
    }

    fun setUIState(state: BaseMemoScreen) {
        this.uiState = state
    }

}

class NavigationHandler() {

}
