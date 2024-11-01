import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import screen.CategoryScreen
import screen.DetailMemoScreen
import screen.HomeScreen
import screen.MemoListScreen
import viewmodel.MemoViewModel
import viewmodel.NavigationHandler
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NavigationHandlerTest {

    private lateinit var navigationHandler: NavigationHandler

    @BeforeTest
    fun setUp() {
        navigationHandler = NavigationHandler()
    }

    @Nested
    inner class SetScreenTest {
        @Test
        fun `setScreen change to newScreen` () {
            val newScreen = CategoryScreen(HomeScreen(), true)
            navigationHandler.setScreen(newScreen)
            val currentScreen = navigationHandler.getCurrentScreen()

            assertTrue(currentScreen is CategoryScreen, "currentScreen: $currentScreen, newScreen: $newScreen")
        }
    }


    @Nested
    inner class NavigateToHomeScreenTest {
        @Test
        @DisplayName("currentScreen이 HomeScreen으로 변경됨")
        fun `navigateToHomeScreen change currentScreen to HomeScreen` () {
            navigationHandler.setScreen(CategoryScreen(HomeScreen(), true))
            navigationHandler.navigateToHomeScreen()
            val currentScreen = navigationHandler.getCurrentScreen()

            assertTrue(currentScreen is HomeScreen, "currenScreen : $currentScreen")
        }
    }

@Nested
inner class NavigateToDetailMemoScreen {
    @Test
    @DisplayName("navigateToDetailMemoScreen에 올바른 아이디값이 입력되었을 때 currentScreen값이 변경됨")
    fun success () {
        navigationHandler.navigateToHomeScreen()
        navigationHandler.navigateToDetailMemoScreen(2)
        val currentScreen = navigationHandler.getCurrentScreen()
        assertTrue(currentScreen is DetailMemoScreen, "currentScreen : $currentScreen")
    }

    @Test
    @DisplayName("navigateToDetailMemoScreen에 잘못된 아이디값(음수)이 입력되었을 때 currentScreen이 DetailMemoScreen이면 안 됨")
    fun failA () {
        navigationHandler.setScreen(MemoListScreen())
        navigationHandler.navigateToDetailMemoScreen(-1)
        val currentScreen = navigationHandler.getCurrentScreen()
        assertAll(
            "if it's not a valid id, should not change currentScreen to DetailMemoScreen",
            { assertFalse(currentScreen is DetailMemoScreen, "DetailMemoScreen으로 변경되면 안 됨") },
            { assertTrue(currentScreen is MemoListScreen, "currentScreen이 MemoList가 아님") }
        )
    }

    @Test
    @DisplayName("navigateToDetailMemoScreen에 잘못된 아이디값(memoList.size < id)이 입력되었을 때 currentScreen이 DetailMemoScreen이면 안 됨")
    fun failB () {
        val memoListSize = MemoViewModel.getInstance().fetchMemos().size + 1
        navigationHandler.setScreen(MemoListScreen())
        navigationHandler.navigateToDetailMemoScreen(memoListSize)
        val currentScreen = navigationHandler.getCurrentScreen()
        assertAll(
            "if it's not a valid id, should not change currentScreen to DetailMemoScreen",
            { assertFalse(currentScreen is DetailMemoScreen, "DetailMemoScreen으로 변경되면 안 됨") },
            { assertTrue(currentScreen is MemoListScreen, "currentScreen이 MemoList가 아님") }
        )
    }
}
}
