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
        /**
         * TODO
         * 1. 전처리 (요청된 연락처를 로컬 db에 저장
         * 2. 서버로 upSync
         * 2-1. 성공 처리 : dirty sync 해제 처리
         * 2-2. 실패 / 오류 처리 : dirty sync 등록
         */
        contactDao.insert(contact.toEntity())
    }

    override fun getAllContact(): List<Contact> {
        return contactDao.getAllContact().map { it.toData() }
    }
}