package screen

import model.Category
import model.Option
import view.input
import view.CONSOLE_MESSAGE_CHOICE_CATEGORY
import view.CONSOLE_MESSAGE_WRONG_INPUT
import view.printMessageAndOptions
import view.printMessage
import viewmodel.NavigationHandler


/*
* 카테고리 선택하는 부분이 두 군데 있음
* 1. 메모를 작성할 때 카테고리 선택
* 2. 메모 목록보기에서 전체목록 or 카테고리별 목록 선택
*
* 각 단계마다 뒤로가기가 있기 때문에 뒤로가기를 눌렀을 때 설정할 스크린(backScreen)과 isWriteMemo라는 속성을 추가함
*
* isWriteMemo = true -> 메모작성으로 들어왔다는 뜻임
* 때문에 input으로 1번을 입력했을 때 isWriteMemo가 참인지 거짓인지 확인하고
* 참이라면 메모작성을 하고 있는 상태이기 때문에 MemoWriteScreen으로 이동시킴
* 이때 MemoWriteScreen에 사용자가 선택한 category값과 MemoWriteScreen에서도 뒤로가기에 설정할 스크린을 보내줌(this = CategoryScreen)
*
* isWriteMemo = false -> 카테고리별 목록 보기에서 카테고리 선택하는 스크린이라는 뜻
* isWriteMemo가 거짓인 경우에는 카테고리별 목록보기를 선택 후 카테고리를 선택하는 스크린이라는 뜻임
* 때문에 선택한 카테고리별로 필터링한 메모리스트를 보여주는 MemoListScreen을 currentScreen으로 set하는 걸로 설정함
*
* 0을 누르면 backScreen으로 currentScreen이 설정되고, 참을 리턴함
*
* 이 외 ->
* 다시 번호를 입력하라는 메세지와 함께 참을 리턴하고, currentScreen이 CategoryScreen이니까 CategoryScreen의 displayView와 showOption을 보여줌
*
* */

class CategoryScreen(private val backScreen: BaseMemoScreen, private val isWriteMemo: Boolean) : BaseMemoScreen {

        override fun displayView() {
            val option: List<Option<Category>> = listOf(
                Option(Category.DAILY), Option(Category.ECONOMY), Option(
                    Category.TECH
                ), Option(Category.ETC)
            )
            val otherOption = " 0. 뒤로가기"

            printMessage(CONSOLE_MESSAGE_CHOICE_CATEGORY)
            printMessageAndOptions(option, otherOption)
        }

        override fun showOptions(navigation: NavigationHandler): Boolean {
            val input = input() ?: return true

            when (input) {
                "1", "2", "3", "4" -> {
                    val category = when (input) {
                        "1" -> Category.DAILY
                        "2" -> Category.ECONOMY
                        "3" -> Category.TECH
                        else -> Category.ETC
                    }

                    if(isWriteMemo) {
                    navigation.setScreen(MemoWriteScreen(category, this))
                    } else {
                    navigation.setScreen(MemoListScreen(category))
                    }
                    return true
                }

                "0" -> {
                    navigation.setScreen(backScreen)
                    return true
                }

                else -> {
                    printMessageAndOptions(CONSOLE_MESSAGE_WRONG_INPUT)
                    return true
                }
            }
        }

    }


