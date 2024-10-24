import model.Category
import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase
import viewmodel.MemoViewModel

fun main(args: Array<String>) {
    println("MAIN ENTRY POINT")

    val memoDatabase: IMemoDatabase = FileMemoDatabase.getInstance()

    val res = memoDatabase.writeMemo(
        listOf(
            Memo(
                id = 1, content = "A", category = Category.ECONOMY
            ),
            Memo(
                id = 2, content = "B", category = Category.TECH
            ),
            Memo(
                id = 3, content = "C", category = Category.ETC
            ),
        )
    )

    ConsoleView(viewModel = MemoViewModel.getInstance()).start()
}

class ConsoleView(private val viewModel: MemoViewModel) {
    fun start() {
        while (true) {
            val state = viewModel.uiState
            state.displayView()
            state.showOptions()
            val selection = readlnOrNull()?.toIntOrNull()
            if (selection != null && selection > 0 && selection <= state.options.size) {
//                TODO: navigation handler
                var screen = viewModel.setUIState(state.options.get(selection - 1).to)

            } else {
                println("Invalid selection. Please try again.")
            }
        }
    }
}