package com.example.delayrequest

data class SyncRequestEvent(
    val itemId: Long,
    val data: Any? = null,
    val func: (() -> Unit)? = null
)
