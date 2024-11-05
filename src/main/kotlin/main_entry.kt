import view.ConsoleView
import viewmodel.MemoViewModel

fun main(args: Array<String>) {

    ConsoleView(viewModel = MemoViewModel.getInstance()).start()
}

