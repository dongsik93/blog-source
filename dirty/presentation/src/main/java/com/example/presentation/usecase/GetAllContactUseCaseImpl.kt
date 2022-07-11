package com.example.presentation.usecase

import com.example.domain.model.Contact
import com.example.domain.repository.ContactRepository
import com.example.domain.usecase.GetAllContactUseCase
import javax.inject.Inject

class GetAllContactUseCaseImpl @Inject constructor(private val repository: ContactRepository) : GetAllContactUseCase {
    override fun invoke(): List<Contact> {
        return repository.getAllContact()
    }
}