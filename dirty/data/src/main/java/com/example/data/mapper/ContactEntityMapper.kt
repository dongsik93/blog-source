package com.example.data.mapper

import com.example.data.db.entity.ContactEntity
import com.example.domain.model.Contact

fun Contact.toEntity(id: Long = 0L): ContactEntity {
    return ContactEntity(
        id = id,
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