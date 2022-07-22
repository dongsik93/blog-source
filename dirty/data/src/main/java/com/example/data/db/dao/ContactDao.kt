package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.db.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContactDao : BaseDao<ContactEntity>() {
    @Query("SELECT * FROM contact")
    abstract fun getAllContact(): Flow<List<ContactEntity>>

    @Query("SELECT id FROM contact WHERE name = :name")
    abstract fun getContactIdByName(name: String): Long

    @Query("SELECT * FROM contact WHERE id = :id")
    abstract fun getContactById(id: Long): List<ContactEntity>

    @Query("DELETE from contact WHERE id = :id")
    abstract fun deleteByContactId(id: Long)
}