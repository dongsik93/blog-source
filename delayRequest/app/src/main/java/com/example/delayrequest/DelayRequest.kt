package com.example.delayrequest

import kotlinx.coroutines.*

abstract class DelayRequest {

    /* 성공시 작업 */
    protected abstract fun process(request: SyncRequestEvent)

    /* 실패시 작업 */
    protected abstract fun cancelProcess(request: SyncRequestEvent)

    protected abstract fun taskTag(): String

    /**
     * cancelableJob
     * @desc 정상적으로 delay 시간이 지난 request 에 대해서 process, 중간에 실패시 cancelProcess 진행
     */
    fun cancelableJob(request: SyncRequestEvent, scope: CoroutineScope): Job {
        return scope.launch {
            launch {
                try {
                    delay(DEFAULT_DELAY)
                    process(request)
                } catch (e: CancellationException) {
                    cancelProcess(request)
                }
            }.join()
        }
    }

    companion object {
        private const val DEFAULT_DELAY = 1500L
    }
}