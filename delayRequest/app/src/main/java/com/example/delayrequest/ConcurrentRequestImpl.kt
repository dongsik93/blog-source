package com.example.delayrequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.ConcurrentHashMap

class ConcurrentRequestImpl : DelayRequest() {

    /* thread-safe 한 map 객체 */
    private val currentHashMap: ConcurrentHashMap<Long, Job> = ConcurrentHashMap()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun process(request: SyncRequestEvent) {
        request.func?.invoke()
    }

    override fun cancelProcess(request: SyncRequestEvent) {
        println("Cancel Process : $request")
    }

    fun sendEvent(itemId: Long, event: SyncRequestEvent) {
        val queueSize = currentHashMap.size

        /* 작업 완료는 큐에서 삭제 */
        val done = currentHashMap.filter { it.value.isCompleted }
        val canceled = done.count { it.value.isCancelled }
        done.forEach {
            currentHashMap.remove(it.key)
        }

        println("[${TAG}] Item Id : $itemId, [Queue] size:$queueSize, done=${done.size}, cancel=${canceled}")

        /* 기본동작 : 동작중인 task 가 존재하면 취소 */
        currentHashMap.forEach {
            if (it.key == itemId) {
                println("cancel previous task: ${taskTag()}")
                it.value.cancel()
            }
        }

        currentHashMap[itemId] = cancelableJob(event, ioScope)
    }

    override fun taskTag() = TAG

    companion object {
        private const val TAG = "ConcurrentRequestImpl"
    }
}