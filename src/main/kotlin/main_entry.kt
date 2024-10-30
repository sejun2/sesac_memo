import model.Category
import model.Memo
import util.FileMemoDatabase
import util.IMemoDatabase
import viewmodel.MemoViewModel

fun main(args: Array<String>) {
    println("MAIN ENTRY POINT")

    // below is only for test
    val memoDatabase: IMemoDatabase = FileMemoDatabase.getInstance()

    val res = memoDatabase.writeMemo(
        listOf(
            Memo(
                id = 1, content = "A", Category.ETC
            ),
            Memo(
                id = 2, content = "B", Category.TECH
            ),
            Memo(
                id = 3, content = "C", Category.ECONOMY
            ),
        )
    )

    ConsoleView(viewModel = MemoViewModel.getInstance()).start()
}

class ConsoleView(private val viewModel: MemoViewModel) {
    fun start() {
        //TODO: run views
    }
}