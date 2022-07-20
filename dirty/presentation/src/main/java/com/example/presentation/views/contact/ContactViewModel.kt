package com.example.presentation.views.contact

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
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val saveContactUseCase: SaveContactUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : BaseViewModel(ioDispatcher) {

    init {
        onIo {
            event(ContactEvents.LoadAllContact(getAllContactUseCase()))
        }
    }

    fun saveContact(contact: Contact) {
        onIo {
            saveContactUseCase.invoke(ContactParam(contact, SyncFlag.CREATE))
        }
    }

    sealed class ContactEvents : Events {
        data class LoadAllContact(val contactList: List<Contact>) : ContactEvents()
    }
}