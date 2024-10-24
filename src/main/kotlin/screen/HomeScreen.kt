package screen

import model.Option
import util.CONSOLE_MESSAGE_HOME_1
import util.CONSOLE_MESSAGE_HOME_2
import util.printMessageAndOptions
import viewmodel.MemoViewModel


class HomeScreen(private val viewModel: MemoViewModel) : BaseMemoScreen() {

    override fun displayView() {
        printMessageAndOptions(CONSOLE_MESSAGE_HOME_1)
    }

    override fun showOptions() = printMessageAndOptions(CONSOLE_MESSAGE_HOME_2, options)


   override val options = listOf(Option("메모 작성", WriteMomoScreen), Option("메모 목록 보기"), Option("종료"))





}
