package screen

import model.Option
import util.NavigationHandler
import view.*
import view.printMessage


class HomeScreen() : BaseMemoScreen {


    override fun displayView() {
        val option: List<Option<String>> = listOf(Option("메모작성"), Option("메모 목록보기"))
        val otherOption = " 0. 종료 "

        printMessage(CONSOLE_MESSAGE_HOME_SCREEN_1)
        printMessage(CONSOLE_MESSAGE_HOME_SCREEN_2)
        printMessageAndOptions(option, otherOption)
    }

    /*
    *
    * 1번을 누를 때 ->
    * currentScreen이 HomeScreen인지 확인하고 아니라면 홈스크린으로 바꾸어줌
    * 그리고 카테고리를 선택하는 스크린(CategoryScreen)으로 currentScreen을 변경함
    * 그 후 true를 리턴해 ConsoleView의 start() 함수에서 showScreen()가 참을 리턴하게 되고
    * while문이 계속 실행됨. 그리고 다시 showScreen()가 실행됨. 스크린마다의 displayView()랑 showOption()이 실행됨
    *
    *
    * 2번을 누를 때 -> 전체 메모목록 or 카테고리별 메모 목록 중에 선택하는 스크린으로 currentScreen을 설정함
    * 그리고 참을 리턴하고 다시 showScreen()가 실행됨. currentScreen이 MemoListTypeScreen으로 바뀌었고,
    * 이 스크린의 displayView()와 showOption()이 실행됨(스크린마다 다름!)
    *
    * 0번을 누를 때 -> false를 리턴하고 while문이 종료되면서(showScreen()이 false가 되니까) 콘솔 프로그램이 종료됨
    * 이 외 ->
    * 번호를 다시 입력하라는 메세지를 보여주고 트루를 리턴함
    * 이 때 currentScreen이 HomeScreen이니까 HomeScreen의 displayView()와 showOption()을 보여줌
    * */
    override fun showOptions(navigation: NavigationHandler): Boolean {
        val input = input() ?: return true

        when(input) {
            "1" -> {
                if (navigation.getCurrentScreen() !is HomeScreen) {  // 홈스크린이 아니면 강제로 홈스크린으로 설정
                    navigation.navigateToHomeScreen()
                }
                navigation.setScreen(CategoryScreen(HomeScreen(), true))
                return true
            }
            "2" -> {
                navigation.setScreen(MemoListTypeScreen())
                return true
            }
            "0" -> return false
            else -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }
        }

    }
}
