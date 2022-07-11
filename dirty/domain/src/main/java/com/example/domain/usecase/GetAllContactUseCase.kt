package com.example.domain.usecase

import com.example.domain.model.Contact
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class GetAllContactUseCase @Inject constructor(private val repository: ContactRepository) {
    operator fun invoke(): List<Contact> = repository.getAllContact()
}