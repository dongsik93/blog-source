package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.db.entity.DirtyFlagEntity

@Dao
interface DirtyFlagDao {
    @Query("SELECT * FROM dirty_flag")
    fun loadDirtyFlag(): List<DirtyFlagEntity>
}