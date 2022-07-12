package com.example.domain.usecase

import com.example.domain.model.Contact
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class SaveContactUseCase @Inject constructor(private val repository: ContactRepository) {
    operator fun invoke(contact: Contact) {
        repository.saveContact(contact)
    }
}