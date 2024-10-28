package screen

import model.Option
import view.CONSOLE_MESSAGE_HOME_SCREEN
import view.CONSOLE_MESSAGE_HOME_SCREEN_1
import view.printMessageAndOptions
import view.stringPrinter.printMessage
import viewmodel.MemoViewModel

class HomeScreen() : BaseMemoScreen() {

    override fun displayView() {
        printMessage(CONSOLE_MESSAGE_HOME_SCREEN)
        printMessage(CONSOLE_MESSAGE_HOME_SCREEN_1)
    }

    override fun showOptions() = printMessageAndOptions(option, message)

    val option: List<Option<String>> = listOf(Option("메모작성"), Option("메모 목록보기"))
    val message = " 0. 종료 "




}
