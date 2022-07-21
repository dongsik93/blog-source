package com.example.domain.model

interface DirtyRequest {
    fun preExecute()
    fun onSuccess()
    fun onFailure()
}