package com.example.domain.usecase

import com.example.domain.model.Contact
import com.example.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactUseCase @Inject constructor(private val repository: ContactRepository) {
    operator fun invoke(): Flow<List<Contact>> = repository.getAllContact()
}