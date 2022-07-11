package com.example.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.model.Events
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Events>()
    val eventFlow: SharedFlow<Events> = _eventFlow.asSharedFlow()

    fun event(event: Events) {
        viewModelScope.launch { _eventFlow.emit(event) }
    }
}