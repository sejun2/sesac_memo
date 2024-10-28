package screen

import model.Category
import model.Option
import view.CONSOLE_MESSAGE_CHOICE_CATEGORY_1
import view.printMessageAndOptions
import view.stringPrinter.printMessage
import viewmodel.MemoViewModel


class CategoryScreen(private val viewModel: MemoViewModel): BaseMemoScreen() {
    override fun displayView() {
        printMessage(CONSOLE_MESSAGE_CHOICE_CATEGORY_1)
    }

    override fun showOptions() = printMessageAndOptions(option, message)

    val option: List<Option<Category>> = listOf(Option(Category.DAILY), Option(Category.ECONOMY), Option(Category.TECH), Option(Category.ETC))
    val message = " 0. 뒤로가기"
}
