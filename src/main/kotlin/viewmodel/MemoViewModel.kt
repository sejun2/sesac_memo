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
        currentScreen = DetailMemoScreen(id)
    }


}
