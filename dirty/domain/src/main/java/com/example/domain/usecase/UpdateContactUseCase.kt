package com.example.domain.usecase

import com.example.domain.model.ContactParam
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(private val repository: ContactRepository) {
    operator fun invoke(contactParam: ContactParam) {
        repository.updateContact(contactParam)
    }
}