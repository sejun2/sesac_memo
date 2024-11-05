package view

import util.NavigationHandler

class ConsoleView() {
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