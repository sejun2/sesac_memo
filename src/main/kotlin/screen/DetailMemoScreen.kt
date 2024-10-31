package screen

import model.Option
import view.CONSOLE_MESSAGE_DETAIL_MEMO_1
import view.ConsoleIo.printMessage
import view.printMessageAndOptions
import viewmodel.MemoViewModel
import viewmodel.NavigationHandler

class DetailMemoScreen(private val memoId: Int) : BaseMemoScreen {
    val viewModel = MemoViewModel.getInstance()
    val memoList = viewModel.fetchMemos()
    val selectedMemo = memoList.find{ it.id == memoId}

    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("수정"), Option("삭제"), Option("뒤로가기"), Option("홈으로") )

        printMessage(CONSOLE_MESSAGE_DETAIL_MEMO_1)
        printMessageAndOptions(option)
        printMessage("[ ${selectedMemo?.category} ]")
        printMessage("[ 내용 ]")
    }

    override fun showOptions(navigation: NavigationHandler): Boolean {
        // 여기서 이제 수정, 삭제 구현하면 됩니다!
        return true
    }
}