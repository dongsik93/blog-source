package com.example.presentation.views.contact

import androidx.lifecycle.viewModelScope
import com.example.domain.model.Contact
import com.example.domain.model.ContactParam
import com.example.domain.model.IoDispatcher
import com.example.domain.model.SyncFlag
import com.example.domain.usecase.GetAllContactUseCase
import com.example.domain.usecase.SaveContactUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.Events
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val saveContactUseCase: SaveContactUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    init {
        viewModelScope.launch(ioDispatcher) {
            event(ContactEvents.LoadAllContact(getAllContactUseCase()))
        }
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch(ioDispatcher) {
            saveContactUseCase.invoke(ContactParam(contact, SyncFlag.CREATE))
        }
    }

    sealed class ContactEvents : Events {
        data class LoadAllContact(val contactList: List<Contact>) : ContactEvents()
    }
}