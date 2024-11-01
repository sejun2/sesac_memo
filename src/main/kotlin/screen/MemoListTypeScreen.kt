package screen

import model.Option
import view.*
import viewmodel.NavigationHandler


/*
* 전체 메모 목록 or 카테고리별 메모 목록보기 둘 중 하나를 선택하는 스크린
*
* 1번을 입력 시 -> MemoListScreen()으로 currentScreen이 변경됨
* 이 때 MemoListScreen()에 category를 파라미터로 안 넘겼기 때문에 전체목록 (memoList)가 보일 것임
* (MemoListScreen의 displayMemoList(14번째 줄 참고. category가 null이면 ?: 뒤의 memoList가 displayMemoList가 됨 ))
*
* 2번을 입력 시 currentScreen = CategoryScreen()이 되고, isWriteMemo가 false이므로 메모작성 상태가 아님을 알려줌
* 따라서 CategoryScreen에서 카테고리를 입력했을 때, isWriteMemo가 false이므로 navigation.setScreen(MemoListScreen(category))가 되고
* MemoListScreen에 파라미터로 category가 넘어갔으므로(null이 아님) displayMemoList가 카테고리로 필터링되어서 보여질 것임
*
* 0번 입력 시 -> currentScreen = HomeScreen이 됨
*
* 이 외 -> 다시 번호를 입력하라는 메세지와 함께 참을 리턴하고 해당 스크린(MemoListTypeScreen)의 displayView()와 showOption()이 실행됨
*
* */
class MemoListTypeScreen() : BaseMemoScreen {
    override fun displayView() {
        val option: List<Option<String>> = listOf(
            Option("전체 메모보기"), Option("카테고리별 메모보기"))

        printMessage(CONSOLE_MESSAGE_SHOW_MEMO_LIST_SELECTOR)
        printMessageAndOptions(option, " 0. 뒤로가기")
    }

    override fun showOptions(navigation: NavigationHandler): Boolean {
        val input = input() ?: return true

        when(input) {
            "1" -> {
                navigation.setScreen(MemoListScreen())
                return true
            }
            "2" -> {
                navigation.setScreen(CategoryScreen(MemoListTypeScreen(), false))
                return true
            }
            "0" -> {
                navigation.navigateToHomeScreen()
                return true
            }
            else -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }
        }
    }
}