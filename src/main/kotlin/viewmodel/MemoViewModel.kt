package viewmodel

import model.Category
import model.Memo
import screen.BaseMemoScreen
import screen.DetailMemoScreen
import screen.HomeScreen
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

    var memos: List<Memo> = mutableListOf()
    val fileDemo: IMemoDatabase = FileMemoDatabase.getInstance()

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

    fun modifyMemos(id:Int, content: String, category: Category) {
        fileDemo.modifyMemo(id, content, category)
    }

    fun getMemoById (id: Int): Memo? {
        val memoList = fetchMemos()
        val selectedMemo = memoList.find { it.id == id }
        return selectedMemo
    }

}

class NavigationHandler() {
    private var currentScreen: BaseMemoScreen = HomeScreen()

    fun getCurrentScreen(): BaseMemoScreen = currentScreen

    fun setScreen(screen: BaseMemoScreen) {
        currentScreen = screen
    }

    fun navigateToHomeScreen() {
        currentScreen = HomeScreen()
    }

    fun navigateToDetailMemoScreen(id: Int) {
        val memoListLength = MemoViewModel.getInstance().fetchMemos().size
        when{
         id < 0 || id > memoListLength -> return
         id in 1..memoListLength -> currentScreen = DetailMemoScreen(id)
         else -> return
        }
    }
}
