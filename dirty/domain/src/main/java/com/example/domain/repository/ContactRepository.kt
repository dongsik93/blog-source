package com.example.domain.repository

import com.example.domain.model.Contact

interface ContactRepository {
    fun saveContact(contact: Contact)

    fun getAllContact(): List<Contact>
}