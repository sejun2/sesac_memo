package view

import viewmodel.MemoViewModel
import viewmodel.NavigationHandler

class ConsoleView(private val viewModel: MemoViewModel) {
    private var navigation = NavigationHandler()

    private fun showScreen(): Boolean {
        val screen = navigation.getCurrentScreen()
        screen.displayView()
        return screen.showOptions(navigation)
    }

    fun start() {
        while (true) {
            val consoleRunning = showScreen()
            if(!consoleRunning) break
        }
    }
}