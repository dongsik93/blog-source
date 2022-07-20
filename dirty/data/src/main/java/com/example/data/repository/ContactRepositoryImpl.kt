package com.example.data.repository

import com.example.data.db.dao.ContactDao
import com.example.data.db.dao.DirtyFlagDao
import com.example.data.db.entity.DirtyFlagEntity
import com.example.data.mapper.toData
import com.example.data.mapper.toDirty
import com.example.data.mapper.toEntity
import com.example.domain.model.Contact
import com.example.domain.model.ContactParam
import com.example.domain.repository.ContactRepository
import javax.inject.Inject
import kotlin.random.Random

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val dirtyFlagDao: DirtyFlagDao,
) : ContactRepository {
    override fun saveContact(contactParam: ContactParam) {

        // 1. 전처리
        contactDao.insert(contactParam.contact.toEntity())

        // 2. upSync
        // random 하게 서버 실패 / 성공동작
        val contactId = Random.nextLong(10)
        if (contactId.toInt() % 2 == 0) {
            // 성공처리
            dirtyFlagDao.deleteDirtyFlagById(contactId)
        } else {
            // 실패처리
            val alreadyRegistered = dirtyFlagDao.loadByContactId(contactId)
            if (alreadyRegistered == null) {
                // 새로운 연락처
                println("신규 등록 연락처 insert dirtyFlag : $contactId")
                dirtyFlagDao.insert(DirtyFlagEntity(contactId))
            } else {
                // 이미 실패한 기록이 있는 연락처
                val updateEntity = DirtyFlagEntity.merge(contactParam.contact.toDirty(contactParam.syncFlag), alreadyRegistered)
                if (updateEntity.isMaximumRetryCountReached()) {
                    println("최대 retry 도달 : $contactId")
                } else {
                    println("기존 등록 연락처 update dirtyFlag retryCount : $contactId")
                    dirtyFlagDao.update(updateEntity)
                }
            }
        }
    }

    override fun getAllContact(): List<Contact> {
        return contactDao.getAllContact().map { it.toData() }
    }
}