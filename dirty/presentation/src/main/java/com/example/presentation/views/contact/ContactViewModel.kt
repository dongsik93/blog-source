package com.example.presentation.views.contact

import com.example.domain.model.Contact
import com.example.domain.model.ContactParam
import com.example.domain.model.IoDispatcher
import com.example.domain.model.SyncFlag
import com.example.domain.usecase.DeleteContactUseCase
import com.example.domain.usecase.GetAllContactUseCase
import com.example.domain.usecase.SaveContactUseCase
import com.example.domain.usecase.UpdateContactUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ContactAction
import com.example.presentation.model.Events
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val saveContactUseCase: SaveContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : BaseViewModel(ioDispatcher) {

    init {
        onIo {
            getAllContactUseCase().collect {
                event(
                    ContactEvents.LoadAllContact(it)
                )
            }
        }
    }

    fun contactAction(contact: Contact, action: ContactAction) {
        onIo {
            when(action) {
                ContactAction.CREATE -> saveContactUseCase.invoke(ContactParam(contact, SyncFlag.CREATE))
                ContactAction.DELETE -> deleteContactUseCase.invoke(ContactParam(contact, SyncFlag.DELETE))
                ContactAction.UPDATE -> updateContactUseCase.invoke(ContactParam(contact, SyncFlag.UPDATE))
            }
        }
    }

    sealed class ContactEvents : Events {
        data class LoadAllContact(val contactList: List<Contact>) : ContactEvents()
    }
}