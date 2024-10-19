import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.whileSelect
import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase
import viewmodel.MemoUIState
import viewmodel.MemoViewModel
import java.util.Scanner

fun main(args: Array<String>) {
    println("MAIN ENTRY POINT")

    val memoDatabase: IMemoDatabase = FileMemoDatabase.getInstance()

    val res = memoDatabase.writeMemo(
        listOf(
            Memo(
                id = 1, content = "A"
            ),
            Memo(
                id = 2, content = "B"
            ),
            Memo(
                id = 3, content = "C"
            ),
        )
    )

    ConsoleView(viewModel = MemoViewModel()).start()
}

class ConsoleView(private val viewModel: MemoViewModel) {
    fun start() {
        while (true) {
            val state = viewModel.uiState
            state.displayView()
            state.showOptions()
            print("Select an option: ")
            val selection = readLine()?.toIntOrNull()
            if (selection != null && selection > 0 && selection <= state.options.size) {
                viewModel.setUIState(state.options.get(selection - 1).to)
            } else {
                println("Invalid selection. Please try again.")
            }
        }
    }
}