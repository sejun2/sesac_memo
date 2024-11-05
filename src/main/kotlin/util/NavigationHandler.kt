package util

import screen.BaseMemoScreen
import screen.DetailMemoScreen
import screen.HomeScreen

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
