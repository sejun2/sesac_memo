package screen
import util.NavigationHandler

interface BaseMemoScreen {
     fun displayView()
     fun showOptions(navigation: NavigationHandler): Boolean
}

