package com.example.delayrequest

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job

sealed class ActorJob
data class InJob(val itemId: Long, val data: Boolean) : ActorJob()
data class DoJob(val ack: CompletableDeferred<Job?>) : ActorJob()