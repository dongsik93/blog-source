package com.example.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.SyncFlag

@Entity(tableName = "dirty_flag")
data class DirtyFlagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "sync_flag")
    var syncFlag: SyncFlag = SyncFlag.CREATE,

    @ColumnInfo(name = "retry_count")
    var retryCount: Int = 0
) {

    /**
     * isMaximumRetryCountReached
     * @desc max retry count 판단
     */
    fun isMaximumRetryCountReached() = retryCount > 5

    companion object {
        /**
         * merge
         * @desc priority 에 따른 syncFlag 선정 및 retryCount 증가
         */
        fun merge(oldEntity: DirtyFlagEntity, newEntity: DirtyFlagEntity): DirtyFlagEntity {
            val applyFlag =
                if (newEntity.syncFlag.priority > oldEntity.syncFlag.priority) newEntity.syncFlag
                else oldEntity.syncFlag

            val retryCount = oldEntity.retryCount + 1

            return DirtyFlagEntity(
                id = oldEntity.id,
                syncFlag = applyFlag,
                retryCount = retryCount
            )
        }
    }
}