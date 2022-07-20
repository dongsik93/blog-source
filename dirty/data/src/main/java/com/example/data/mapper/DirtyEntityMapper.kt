package com.example.data.mapper

import com.example.data.db.entity.DirtyFlagEntity
import com.example.domain.model.Contact
import com.example.domain.model.SyncFlag

fun Contact.toDirty(syncFlag: SyncFlag): DirtyFlagEntity {
    return DirtyFlagEntity(
        id = 0,
        syncFlag = syncFlag,
        retryCount = 0
    )
}