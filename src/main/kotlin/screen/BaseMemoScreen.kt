package screen
import model.Option
import viewmodel.NavigationHandler

interface BaseMemoScreen {
     fun displayView()
     fun showOptions(navigation: NavigationHandler): Boolean
}

