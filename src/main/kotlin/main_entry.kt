import model.Category
import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase
import view.ConsoleView
import viewmodel.MemoViewModel

fun main(args: Array<String>) {
    println("MAIN ENTRY POINT")

    ConsoleView(viewModel = MemoViewModel.getInstance()).start()
}

