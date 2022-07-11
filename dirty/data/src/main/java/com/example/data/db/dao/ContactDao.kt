package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.db.entity.ContactEntity

@Dao
abstract class ContactDao() : BaseDao<ContactEntity>() {
    @Query("SELECT * FROM contact")
    abstract fun getAllContact(): List<ContactEntity>
}