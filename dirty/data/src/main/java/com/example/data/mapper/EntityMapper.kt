package com.example.data.mapper

import com.example.data.db.entity.ContactEntity
import com.example.domain.model.Contact

fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = 0,
        name = name,
        phoneNumber = phoneNumber
    )
}

fun ContactEntity.toData(): Contact {
    return Contact(
        name = name,
        phoneNumber = phoneNumber
    )
}