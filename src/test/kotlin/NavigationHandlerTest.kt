import org.junit.jupiter.api.Test
import screen.CategoryScreen
import screen.DetailMemoScreen
import screen.HomeScreen
import viewmodel.NavigationHandler
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

class NavigationHandlerTest {

    private lateinit var navigationHandler: NavigationHandler

    @BeforeTest
    fun setUp() {
        navigationHandler = NavigationHandler()
    }

    @Test

    fun `setScreen should change to newScreen` () {
        val newScreen = CategoryScreen(HomeScreen(), true)
        navigationHandler.setScreen(newScreen)
        val currentScreen = navigationHandler.getCurrentScreen()

        assertTrue(currentScreen is CategoryScreen, "currentScreen: $currentScreen, newScreen: $newScreen")
    }

    @Test
    fun `navigateToHomeScreen_should_change_currentScreen_to_HomeScreen` () {
        navigationHandler.setScreen(CategoryScreen(HomeScreen(), true))
        navigationHandler.navigateToHomeScreen()
        val currentScreen = navigationHandler.getCurrentScreen()

        assertTrue(currentScreen is HomeScreen, "currenScreen : $currentScreen")
    }

    @Test
    fun `navigateToDetailMemoScreen_should_set_currentScreen_to_HomeScreen` () {
        navigationHandler.navigateToDetailMemoScreen(2)
        val currentScreen = navigationHandler.getCurrentScreen()

        assertTrue(currentScreen is DetailMemoScreen, "currentScreen : $currentScreen")
    }


}