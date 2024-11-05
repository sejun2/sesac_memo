package screen

import model.Memo
import model.Option
import util.NavigationHandler
import view.*
import viewmodel.MemoViewModel

class EditMemoScreen(private val id: Int, private val viewModel: MemoViewModel = MemoViewModel.getInstance()) :
    BaseMemoScreen {
    private val selectedMemo = viewModel.getMemoById(id)

    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("뒤로가기"), Option("홈으로"))

        printMessage(CONSOLE_MESSAGE_EDIT_MEMO)
        printMessageAndOptions(option)
        printMessage("[ category ] ${selectedMemo?.category} ")
        printMessage("[ content ] ${selectedMemo?.content}")


    }


    override fun showOptions(navigation: NavigationHandler): Boolean {
        when (val input = input()) {
            "1" -> navigation.setScreen(DetailMemoScreen(id))
            "2" -> navigation.navigateToHomeScreen()
            null -> navigation.setScreen((MemoListScreen()))
            else -> {
                selectedMemo?.let {
                    viewModel.modifyMemos(Memo(selectedMemo.id, input, selectedMemo.category))
                    navigation.setScreen((MemoListScreen()))
                }
            }
        }

        return true
    }

}