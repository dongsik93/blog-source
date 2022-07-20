package com.example.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.IoDispatcher
import com.example.presentation.model.Events
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Events>()
    val eventFlow: SharedFlow<Events> = _eventFlow.asSharedFlow()

    fun event(event: Events) {
        viewModelScope.launch { _eventFlow.emit(event) }
    }

    /**
     * onMain
     * @desc use mainDispatcher
     */
    inline fun onMain(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch {
        body(this)
    }

    /**
     * onIo
     * @desc use ioDispatcher
     */
    inline fun onIo(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(ioDispatcher) {
        body(this)
    }
}