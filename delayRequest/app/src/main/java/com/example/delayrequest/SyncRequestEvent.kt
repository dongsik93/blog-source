package com.example.delayrequest

data class SyncRequestEvent(
    val data: Any? = null,
    val func: (() -> Unit)? = null
)
