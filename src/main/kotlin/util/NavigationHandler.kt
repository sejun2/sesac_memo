package util

import screen.BaseMemoScreen
import screen.DetailMemoScreen
import screen.HomeScreen
import viewmodel.MemoViewModel

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