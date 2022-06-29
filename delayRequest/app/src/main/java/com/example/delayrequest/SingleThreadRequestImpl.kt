package com.example.delayrequest

import kotlinx.coroutines.*

@ObsoleteCoroutinesApi
class SingleThreadRequestImpl : DelayRequest() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val dispatcher = newSingleThreadContext("DelayRequestContext")

    /* 작업 큐 */
    private val currentJobs: MutableMap<Long, Job> = mutableMapOf()

    override fun process(request: SyncRequestEvent) {
        request.func?.invoke()
    }

    override fun cancelProcess(request: SyncRequestEvent) {
        println("Cancel Process : $request")
    }

    override fun taskTag() = TAG

    fun sendEventWithCoroutine(itemId: Long, event: SyncRequestEvent) {
        scope.launch(dispatcher) {
            /* 작업 완료는 큐에서 삭제 */
            val done = currentJobs.filter { it.value.isCompleted }
            val canceled = done.count { it.value.isCancelled }
            done.forEach {
                currentJobs.remove(it.key)
            }

            currentJobs.forEach {
                if (it.key == itemId) {
                    it.value.cancel()
                }
            }
            currentJobs[itemId] = cancelableJob(event, this)
        }
    }

    companion object {
        private const val TAG = "SingleThreadRequestImpl"
    }
}