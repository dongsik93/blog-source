package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.db.entity.DirtyFlagEntity

@Dao
abstract class DirtyFlagDao : BaseDao<DirtyFlagEntity>() {
    @Query("SELECT * FROM dirty_flag")
    abstract fun loadDirtyFlag(): List<DirtyFlagEntity>
}