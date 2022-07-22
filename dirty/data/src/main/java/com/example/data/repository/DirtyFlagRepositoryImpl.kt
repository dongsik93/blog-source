package com.example.data.repository

import com.example.data.db.dao.ContactDao
import com.example.data.db.dao.DirtyFlagDao
import com.example.data.db.entity.DirtyFlagEntity
import com.example.domain.model.SyncFlag
import com.example.domain.repository.DirtyFlagRepository
import javax.inject.Inject

class DirtyFlagRepositoryImpl @Inject constructor(
    private val dirtyFlagDao: DirtyFlagDao,
    private val contactDao: ContactDao,
) : DirtyFlagRepository {
    override fun syncDirty(): Boolean {
        val allDirtyData = dirtyFlagDao.loadDirtyFlag()

        allDirtyData.filter { it.syncFlag == SyncFlag.CREATE }.also { createList ->
            if (createList.isNotEmpty()) requestSync(createList)
        }
        allDirtyData.filter { it.syncFlag == SyncFlag.UPDATE }.also { updateList ->
            if (updateList.isNotEmpty()) requestSync(updateList)
        }
        allDirtyData.filter { it.syncFlag == SyncFlag.DELETE }.also { deleteList ->
            if (deleteList.isNotEmpty()) requestSync(deleteList)
        }

        // upSync 후처리
        dirtyFlagDao.deleteAll()

        return true
    }

    private fun requestSync(list: List<DirtyFlagEntity>) {
        list.forEach { entity ->
            val contact = contactDao.getContactById(entity.id)
            println("Contact Request : $contact")
        }
    }
}