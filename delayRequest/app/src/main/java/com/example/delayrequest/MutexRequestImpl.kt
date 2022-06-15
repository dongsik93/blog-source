package com.example.delayrequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ScheduledThreadPoolExecutor

class MutexRequestImpl : DelayRequest() {

    /* 싱글 스레드 */
    private val dispatcher = ScheduledThreadPoolExecutor(1).apply { removeOnCancelPolicy = true }.asCoroutineDispatcher()

    /* 싱글 스레드 scope */
    private val scope = CoroutineScope(dispatcher)

    /* Mutual Exclusion */
    private val mutex = Mutex()

    /* 작업 큐 */
    private val currentJobs: MutableMap<Long, Job> = mutableMapOf()

    override fun process(request: SyncRequestEvent) {
        request.func?.invoke()
    }

    override fun cancelProcess(request: SyncRequestEvent) {
        println("Cancel Process : $request")
    }

    fun sendEvent(itemId: Long, event: SyncRequestEvent) {
        scope.launch {
            mutex.withLock(currentJobs) {
                val queueSize = currentJobs.size

                /* 작업 완료는 큐에서 삭제 */
                val done = currentJobs.filter { it.value.isCompleted }
                val canceled = done.count { it.value.isCancelled }
                done.forEach {
                    currentJobs.remove(it.key)
                }

                println("[${TAG}] Item Id : $itemId, [Queue] size:$queueSize, done=${done.size}, cancel=${canceled}")

                /* 기본동작 : 동작중인 task 가 존재하면 취소 */
                currentJobs.forEach {
                    if (it.key == itemId) {
                        println("cancel previous task: ${taskTag()}")
                        it.value.cancel()
                    }
                }

                currentJobs[itemId] = cancelableJob(event, scope)
            }
        }
    }

    override fun taskTag() = TAG

    companion object {
        private const val TAG = "MutexRequestImpl"
    }
}