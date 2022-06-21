package com.example.delayrequest

import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class SynchronizedRequestImpl: DelayRequest() {

    private val executor = ScheduledThreadPoolExecutor(1).apply { removeOnCancelPolicy = true }

    private var currentTask: MutableList<ScheduledFuture<*>> = mutableListOf()

    override fun process(request: SyncRequestEvent) {
        request.func?.invoke()
    }

    override fun cancelProcess(request: SyncRequestEvent) {

    }

    fun sendEvent(itemId: Long, event: SyncRequestEvent) {
        synchronized(currentTask) {
            val queueSize = currentTask.size

            val done = currentTask.filter { it.isDone }
            val canceled = done.count { it.isCancelled }

            /* 작업 완료는 큐에서 삭제 */
            currentTask.removeAll(done)

            println("[${TAG}] Item Id : $itemId, [Queue] size:$queueSize, done=${done.size}, cancel=${canceled}")

            /* 기본동작 : 동작중인 task 가 존재하면 취소 */
            currentTask.forEach {
                println("cancel previous task: ${taskTag()}")
                it.cancel(false)
            }

            /* 요청 작업 인큐 */
            currentTask.add(
                executor.schedule(CancelableTask(event), 1500L, TimeUnit.MILLISECONDS)
            )

        }
    }

    inner class CancelableTask (private val request: SyncRequestEvent): Runnable {
        override fun run() {
            println("[${TAG}] task starting... task=${taskTag()}")
            try {
                process(request)
            } catch (e: Exception) {
                println("Error : $e")
            }
        }
    }

    override fun taskTag() = TAG

    companion object {
        private const val TAG = "SynchronizedRequestImpl"
    }
}