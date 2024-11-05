package screen

import model.Category
import model.Memo
import util.ANSI_BLUE
import util.ANSI_BOLD
import util.ANSI_RESET
import util.NavigationHandler
import view.*
import viewmodel.MemoViewModel


/*
* 전체 메모 목록 화면 or 카테고리별 목록 화면을 보여주는 스크린
*
* category == null이면 displayMemoList(실제로 화면에 보여질 메모 리스트)의 let 블록 실행x, ?: 뒤 부분이 실행되면서 memoList(전체 메모 리스트)가 실행됨
* category != null 이면 카테고리 값이 있으므로 let 블록이 실행되고 메모마다 카테고리와 사용자가 선택한 카테고리가 같은 것만 필터링해서 리스트로 보여줌
*
* val memoPreviewNumber = 20는 화면에 보여줄 content를 얼마나 자를건지를 결정하는 수임
* 34번째 줄에서 memo의 content가 memoPreviewNumber보다 작으면 content를 자를 필요도 없으니까 memo.content를 다 보여주고
* 아니라면 0번부터 memoPreviewNumber까지 잘라서보여주고 뒤에 ...을 붙여줌
* 화면에 보여줄 때는 [각 메모의 인덱스 + 1] memoPreviewNumber만큼 자른 memo.content가 보임
* ex: [1] memo입니다. 메모를 작성하다니 너무 신나고 행복하...
*     [2] memo!
*     [3] Lorem ipdssasdsadsadsdsasa...
*
* input 처리)
* 1번 입력 시 -> 뒤로가기임. 이전 스크린은 MemoListTypeScreen(전체 메모 목록 or 카테고리별 메모 목록보기 선택하는 스크린)
* currentScreen을 MemoListTypeScreen로 설정함. 그 후 참을 리턴하고, showScreen()가 실행되고 현재 스크린에 대한 displayView()와 showOption()이
* 실행됨
*
* null일 때 -> 다시 번호를 입력하라는 메세지와 함께 currentScreen이 MemoListScreen이므로
* MemoListScreen의 에 대한 displayView()와 showOption()이 실행됨
*
* input으로 입력한 값이 1보다 작거나 memoList.size 사이에 속하지 않는다면 다시 번호를 입력하라는 메세지와 함꼐 currentScreen이 MemoListScreen이 됨
* 그 후 참을 리턴하고, showScreen()가 실행되고 현재 스크린(MemoListScreen)에 대한 displayView()와 showOption()이 실행됨
*
* 그 외 ->
* 보고자 하는 메모의 번호를 제대로 입력한 것이 됨
* 사용자에게 보여줄때는 인덱스 + 1이 되므로 해당되는 인덱스의 메모를 찾을 때는 -1을 해줌
* 사용자가 입력한 인덱스에 해당하는 메모의 아이디값을 넘겨줌
* currentScreen = DetailMemoScreen이 되고 DetailMemoScreen에서 해당하는 아이디의 메모를 가져와
* 화면에 보여주기만 하면 됨!
*
* */
class MemoListScreen(private val category: Category? = null) : BaseMemoScreen{

    val memoList = MemoViewModel.getInstance().fetchMemos()
    val displayMemoList = category?.let { selectedCategory ->
        memoList.filter { it.category == selectedCategory }
    } ?: memoList

    override fun displayView() {
        when {
            displayMemoList.isEmpty() -> {
                printMessage(CONSOLE_NONE_OF_MEMO)
            }
            else -> {
                printMessage(CONSOLE_MESSAGE_SELECT_SPECIFIC_MEMO)
                printMessage("0. 뒤로가기")

                val memoPreviewNumber = 20

                displayMemoList.mapIndexed { index, memo ->
                    val previewMemo = if(memo.content.length < memoPreviewNumber) {
                        memo.content
                    } else {
                        "${memo.content.slice(0 until memoPreviewNumber)}..."
                    }
                    printMessage("$ANSI_BLUE$ANSI_BOLD[${memo.id}] ${previewMemo}$ANSI_RESET")
                }
            }

        }


    }


    override fun showOptions(navigation: NavigationHandler): Boolean {
        if(displayMemoList.isEmpty()) {
            navigation.navigateToHomeScreen()
            return true
        }

        val input = input() ?: return true

        when {
            input == "0" -> {
                navigation.setScreen(MemoListTypeScreen())
                return true
            }

            !isMemoExist(input.toInt(), displayMemoList)  -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }
            isMemoExist(input.toInt(), displayMemoList) -> {
                val memoId = input.toInt() // 해당 인덱스의 메모 객체를 가져옴
                navigation.navigateToDetailMemoScreen(memoId)  // 메모 객체의 실제 ID 사용
                return true
            }
            else -> {
                printMessage(CONSOLE_MESSAGE_WRONG_INPUT)
                return true
            }
        }
    }

    private fun isMemoExist(memoId: Int, memoList: List<Memo>) : Boolean{
        val res = memoList.find {
            it.id == memoId
        }

        return if(res!=null) true else false
    }
}