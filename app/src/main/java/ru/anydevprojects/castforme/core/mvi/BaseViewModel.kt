package ru.anydevprojects.castforme.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewState, ViewIntent, ViewEvent>(
    initialState: ViewState
) : ViewModel() {

    private val _event = Channel<ViewEvent>(capacity = Channel.CONFLATED)
    val event = _event.receiveAsFlow()

    private val _viewStateFlow: MutableStateFlow<ViewState> =
        MutableStateFlow(initialState)

    val stateFlow: StateFlow<ViewState> = _viewStateFlow.onStart {
        onStart()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = initialState
    )

    var lastContentState: ViewState = stateFlow.value
        private set

    protected fun updateState(block: ViewState.() -> ViewState) {
        _viewStateFlow.update(block)
    }

    abstract fun onIntent(intent: ViewIntent)

    open fun onStart() {}

    protected fun emitEvent(event: ViewEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }
}
