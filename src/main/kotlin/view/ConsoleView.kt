package view

import model.Category
import model.Memo
import screen.CategoryScreen
import screen.CreateMemoScreen
import screen.HomeScreen
import util.printMessageAndOptions
import viewmodel.MemoViewModel
import viewmodel.NavigationHandler

class ConsoleView(private val viewModel: MemoViewModel) {
    private var navigation = NavigationHandler()

    private fun showScreenAndGetInput(): String {
        val screen = navigation.getCurrentScreen()
        screen.displayView()
        screen.showOptions()
        val input = readLine().toString()
        return input
    }


    fun start() {
        while (true) {
            val input = showScreenAndGetInput()
            when(input) {
                "1" -> {
                    navigation.navigateToCategoryScreen()
                    selectCategoryView()
                    if (navigation.getCurrentScreen() !is HomeScreen) {  // 홈스크린이 아니면 강제로 설정
                        navigation.navigateToHomeScreen()
                    }
                }
                "2" -> {
                    navigation.navigateToCategoryScreen()
                    showScreenAndGetInput()
                }
                "0" -> break
                else -> printMessageAndOptions(CONSOLE_MESSAGE_WRONG_INPUT)
            }
        }
    }

    fun selectCategoryView() {
        while (true) {
            val input = showScreenAndGetInput()
            when(input) {
                "1", "2", "3", "4" -> {
                    val category = when(input) {
                        "1" -> Category.DAILY
                        "2" -> Category.ECONOMY
                        "3" -> Category.TECH
                        else -> Category.ETC
                    }
                    navigation.setScreen(CreateMemoScreen())
                    createMemoView(category)
                    return
                }
                "0" -> {
                    navigation.navigateToHomeScreen()
                    return
                }
                else -> {
                    printMessageAndOptions(CONSOLE_MESSAGE_WRONG_INPUT)
                    continue
                }
            }
        }
    }

    fun createMemoView(category: Category) {
        while (true) {
            val input = showScreenAndGetInput()
            when(input) {
                "1" -> {
                    navigation.setScreen(CategoryScreen())
                    selectCategoryView()
                    return
                }
                "2" -> {
                    navigation.navigateToHomeScreen()
                    return
                }
                else -> {
                    val memoList = viewModel.fetchMemos()
                    val newId = if (memoList.isEmpty()) 0 else memoList.maxOf { it.id } + 1
                    val newMemo = Memo(newId, input, category)
                    viewModel.addMemos(newMemo)
                }
            }
        }
    }
}