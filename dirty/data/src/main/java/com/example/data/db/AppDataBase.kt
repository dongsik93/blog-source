package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.DirtyFlagDao
import com.example.data.db.entity.DirtyFlagEntity

@Database(entities = [DirtyFlagEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val dirtyFlagDao: DirtyFlagDao

    companion object {
        const val DB_NAME = "DirtyDataBase"
    }
}