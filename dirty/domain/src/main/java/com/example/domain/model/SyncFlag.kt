package com.example.domain.model

/**
 * SyncFlag
 * @desc 기존 DirtySync Table 존재할 경우 priority 에 따라서 작업 결정
 */
enum class SyncFlag(val priority: Int) {
    UPDATE(1),
    CREATE(2),
    DELETE(3)
}