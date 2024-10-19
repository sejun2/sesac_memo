package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Memo

class MemoViewModel {

    var uiState: MutableStateFlow<MemoUIState> = MutableStateFlow(
        value = MemoUIState.HomeUIState
    )
    var memos: List<Memo> = mutableListOf()

    fun setUIState(uiState: MemoUIState) {
        this.uiState.value = uiState
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

        override val options: List<MemoOption>
            get() = listOf(MemoOption.MODIFY, MemoOption.HELLO, MemoOption.WRITE)
    }

    data object ProfileUIState : MemoUIState() {
        override fun displayView() {
            println("profileUIState")
        }

        override fun showOptions() {
            println(options.toString())
        }

        override val options: List<MemoOption>
            get() = listOf(MemoOption.MODIFY, MemoOption.HELLO)
    }

    data object HelloUIState : MemoUIState() {
        override fun displayView() {
            println("helloUIState")
        }

        override fun showOptions() {
            println(options.toString())
        }

        override val options: List<MemoOption>
            get() = listOf(MemoOption.MODIFY)
    }
    // 기타 등등

    abstract fun displayView()
    abstract fun showOptions()

    abstract val options: List<MemoOption>
}

enum class MemoOption(val uiState: MemoUIState) {
    MODIFY(uiState = MemoUIState.HomeUIState),
    WRITE(uiState = MemoUIState.ProfileUIState),
    HELLO(uiState = MemoUIState.HelloUIState),
}