package screen

import model.Category
import model.Memo
import model.Option
import view.CONSOLE_MESSAGE_WRITE_MEMO
import view.input
import view.printMessageAndOptions
import viewmodel.MemoViewModel
import viewmodel.NavigationHandler


/*
* 메모를 작성하는 스크린
* 1번 입력 시 -> currentScreen = CategoryScreen이 되고, 참을 리턴하면서 CategoryScreen()의 displayView와 showOption이 실행됨
* 2번 입력 시 -> currentScreen = HomeScreen으로 설정함. 참을 리턴하고 HomeScreen의 displayView와 showOption이 실행됨
* 이 외  ->
* 사용자가 값을 입력한 것이므로 Memo를 만들고 memoList에 추가해야함
* input이 null이 아니라면 let 블록이 실행됨(근데 이 부분도 좀 수정이 필요할듯(예외처리)ㅠ)
* newId는 기존의 메모들의 id중에 큰 값을 찾아 그 값의 +1을 하였음
* newMemo는 newId와 사용자가 입력한 input(content가 됨), category를 저장한 데이터 클래스임
* ViewModel의 addMemos를 통해 메모리스트에 새로운 메모를 추가함
* 메모가 저장되면 currentScreen을 HomeScreen으로 설정하고, 참을 리턴하므로 showScreen()의 현재 스크린은 HomeScreen이 되고
* HomeScreen의 displayView와 showOption이 실행됨
*
* */
class MemoWriteScreen(
    private val category: Category,
    private val backScreen: BaseMemoScreen
): BaseMemoScreen {

    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("뒤로가기"), Option("홈으로"))
        printMessageAndOptions(CONSOLE_MESSAGE_WRITE_MEMO, option)
    }

    override fun showOptions(navigation: NavigationHandler): Boolean {
        val input = input() ?: return true

        when(input) {
            "1" -> {
                navigation.setScreen(backScreen)
                return true
            }
            "2" -> {
                navigation.navigateToHomeScreen()
                return true
            }
            else -> {
                    val viewModel = MemoViewModel.getInstance()
                    val memoList = viewModel.fetchMemos()
                    val newId = if (memoList.isEmpty()) 1 else memoList.maxOf { it.id } + 1
                    val newMemo = Memo(newId, input, category)
                    viewModel.addMemos(newMemo)
                    navigation.navigateToHomeScreen()
                return true
            }
        }
    }


}