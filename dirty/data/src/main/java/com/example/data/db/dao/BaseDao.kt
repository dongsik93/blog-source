package com.example.data.db.dao

import androidx.room.*

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: List<T>): List<Long>

    @Update
    abstract fun update(obj: T): Int

    @Update
    abstract fun update(obj: List<T>)

    @Delete
    abstract fun delete(obj: T): Int

    @Delete
    abstract fun delete(obj: List<T>)
}