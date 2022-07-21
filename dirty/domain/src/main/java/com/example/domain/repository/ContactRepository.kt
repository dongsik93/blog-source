package com.example.domain.repository

import com.example.domain.model.Contact
import com.example.domain.model.ContactParam

interface ContactRepository {
    /**
     * TODO : 통합할지, 분리할지 선택 , 각 액션별 다른점이 있는지
     */
    fun saveContact(contactParam: ContactParam)

    fun updateContact(contactParam: ContactParam)

    fun deleteContact(contactParam: ContactParam)

    fun getAllContact(): List<Contact>
}