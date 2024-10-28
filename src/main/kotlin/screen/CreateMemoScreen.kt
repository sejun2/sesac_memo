package screen

import model.Option
import view.CONSOLE_MESSAGE_CREATE_MEMO
import view.printMessageAndOptions
import view.stringPrinter.printMessage

class CreateMemoScreen(): BaseMemoScreen() {
    override fun displayView() {
        printMessage(CONSOLE_MESSAGE_CREATE_MEMO)
    }

    override fun showOptions() {
        printMessageAndOptions("기타옵션 : ", option)
    }

    val option: List<Option<String>> = listOf(Option("뒤로가기"), Option("홈으로"))
}