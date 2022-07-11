package com.example.data.repository

import com.example.data.db.dao.ContactDao
import com.example.data.mapper.toData
import com.example.data.mapper.toEntity
import com.example.domain.model.Contact
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepository {
    override fun saveContact(contact: Contact) {
        contactDao.insert(contact.toEntity())
    }

    override fun getAllContact(): List<Contact> {
        return contactDao.getAllContact().map { it.toData() }
    }
}