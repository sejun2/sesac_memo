package viewmodel

import model.Memo

class MemoViewModel {

    var uiState: MemoUIState = MemoUIState.HomeUIState
    var memos: List<Memo> = mutableListOf()

}


sealed class MemoUIState {
    object HomeUIState : MemoUIState()
    // 기타 등등
}