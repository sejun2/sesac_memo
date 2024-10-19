package viewmodel

import model.Memo

class MemoViewModel {

    var uiState: MemoUIState =
        MemoUIState.HomeUIState
    var memos: List<Memo> = mutableListOf()

    fun setUIState(uiState: MemoUIState) {
        this.uiState = uiState
    }
}


sealed class MemoUIState {
    data object HomeUIState : MemoUIState() {
        override fun displayView() {
            println("homeUIState")
        }

        override fun showOptions() {
            println(options.toString())
        }

        override val options: List<MemoNavigationOption>
            get() = listOf(
                MemoNavigationOption(from = this, to = ProfileUIState, menuName = "HomeToProfile"),
                MemoNavigationOption(from = this, to = HelloUIState, menuName = "HomeToHello"),
            )
    }

    data object ProfileUIState : MemoUIState() {
        override fun displayView() {
            println("profileUIState")
        }

        override fun showOptions() {
            println(options.toString())
        }

        override val options: List<MemoNavigationOption>
            get() = listOf(
                MemoNavigationOption(from = this, to = HomeUIState),
                MemoNavigationOption(from = this, to = HelloUIState),
            )
    }

    data object HelloUIState : MemoUIState() {
        override fun displayView() {
            println("helloUIState")
        }

        override fun showOptions() {
            println(options.toString())
        }

        override val options: List<MemoNavigationOption>
            get() = listOf(
                MemoNavigationOption(from = this, to = HomeUIState),
                MemoNavigationOption(from = this, to = ProfileUIState),
            )
    }
    // 기타 등등

    abstract fun displayView()
    abstract fun showOptions()

    abstract val options: List<MemoNavigationOption>
}

data class MemoNavigationOption(
    val from: MemoUIState,
    val to: MemoUIState,
    val menuName: String = ""


) {
    override fun toString(): String {
        return menuName
    }
}