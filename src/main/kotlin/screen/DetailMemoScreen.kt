package screen

import model.Option
import view.*
import viewmodel.MemoViewModel
import viewmodel.NavigationHandler


class DetailMemoScreen(private val id: Int) : BaseMemoScreen {
    private val selectedMemo = MemoViewModel.getInstance().getMemoById(id)

    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("수정"), Option("삭제"), Option("뒤로가기"), Option("홈으로"))

        printMessage(CONSOLE_MESSAGE_DETAIL_MEMO_1)
        printMessageAndOptions(option)
        printMessage("[ category ] ${selectedMemo?.category} ")
        printMessage("[ content ] \n ${selectedMemo?.content} ")
    }

    override fun showOptions(navigation: NavigationHandler): Boolean {
        val input = input() ?: return true
        when (input) {
            "1" -> navigation.setScreen(EditMemoScreen(id))
            "2" -> navigation.setScreen(DeleteMemoScreen(id))
            "3" -> navigation.setScreen(MemoListScreen())
            "4" -> navigation.navigateToHomeScreen()
            else -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }
        }

        return true
    }
}