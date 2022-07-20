package com.example.domain.repository

import com.example.domain.model.Contact
import com.example.domain.model.ContactParam

interface ContactRepository {
    fun saveContact(contactParam: ContactParam)

    fun getAllContact(): List<Contact>
}