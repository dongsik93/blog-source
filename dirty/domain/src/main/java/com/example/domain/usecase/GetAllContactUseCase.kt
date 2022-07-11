package com.example.domain.usecase

import com.example.domain.model.Contact

interface GetAllContactUseCase {
    operator fun invoke(): List<Contact>
}