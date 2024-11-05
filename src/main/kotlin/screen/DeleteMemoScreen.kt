package screen

import model.Option
import util.NavigationHandler
import view.*
import viewmodel.MemoViewModel

class DeleteMemoScreen(private val id: Int, private val viewModel: MemoViewModel = MemoViewModel.getInstance()): BaseMemoScreen {
    private val selectedMemo = viewModel.getMemoById(id)
    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("삭제하기"), Option("뒤로가기"), Option("홈으로") )
        printMessage(CONSOEL_MESSAGE_DELETE_MEMO)
        printMessageAndOptions(option)
        printMessage("[${selectedMemo?.id} ]")
        printMessage("[ ${selectedMemo?.category} ]")
        printMessage("[ ${selectedMemo?.content} ]")
    }

    override fun showOptions(navigation: NavigationHandler): Boolean {
        val input = input()?: return true
        when(input){
            "1" -> {
                selectedMemo?.let {
                    viewModel.deleteMemos(id)
                    navigation.setScreen(MemoListScreen())
                    return  true
                }
            }
            "2" -> navigation.setScreen(DetailMemoScreen(id))
            "3" -> navigation.navigateToHomeScreen()
            else -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }

        }
        return true
    }
}