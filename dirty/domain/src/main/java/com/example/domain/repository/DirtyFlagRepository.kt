package com.example.domain.repository

interface DirtyFlagRepository {
    fun syncDirty(): Boolean
}