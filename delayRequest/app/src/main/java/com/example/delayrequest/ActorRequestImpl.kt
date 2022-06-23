package com.example.delayrequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor

class ActorRequestImpl : DelayRequest() {
    /* 작업 큐 */
    private val currentJobs: MutableMap<Long, Job> = mutableMapOf()

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun process(request: SyncRequestEvent) {
        request.func?.invoke()
    }

    override fun cancelProcess(request: SyncRequestEvent) {
    }

    override fun taskTag() = TAG

    @ObsoleteCoroutinesApi
    fun sendEvent() = scope.actor<ActorJob> {
        for (job in channel) {
            when (job) {
                is InJob -> {
                    val queueSize = currentJobs.size

                    val done = currentJobs.filter { it.value.isCompleted }
                    val canceled = done.count { it.value.isCancelled }
                    done.forEach {
                        currentJobs.remove(it.key)
                    }

                    println("[${TAG}] Item Id : ${job.itemId}, [Queue] size:$queueSize, done=${done.size}, cancel=${canceled}")

                    currentJobs.forEach {
                        if (it.key == job.itemId) {
                            println("cancel previous task: ${taskTag()}")
                            it.value.cancel()
                        }
                    }
                    currentJobs[job.itemId] = cancelableJob(SyncRequestEvent(func = { println("[DoActor] 실행 : ${job.data}") }), this)
                }
                is DoJob -> job.ack.complete(currentJobs[-1])
            }
        }
    }

    companion object {
        private const val TAG = "ActorRequestImpl"
    }
}