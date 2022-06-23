package com.example.delayrequest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

class MainViewModel : ViewModel() {

    private val mutexRequest = MutexRequestImpl()

    private val concurrentRequest = ConcurrentRequestImpl()

    private val synchronizedRequest = SynchronizedRequestImpl()

    @ObsoleteCoroutinesApi
    private val singleThreadRequestImpl = SingleThreadRequestImpl()

    @ObsoleteCoroutinesApi
    private val actorRequestImpl = ActorRequestImpl()

    fun doMutex(isChecked: Boolean) {
        mutexRequest.sendEvent(
            -1,
            SyncRequestEvent(func = { println("[DoMutex] 실행 : $isChecked") })
        )
    }

    fun doConcurrent(isChecked: Boolean) {
        concurrentRequest.sendEvent(
            -1,
            SyncRequestEvent(func = { println("[DoConcurrent] 실행 : $isChecked") })
        )
    }

    fun doSynchronized(isChecked: Boolean) {
        synchronizedRequest.sendEvent(
            -1,
            SyncRequestEvent(func = { println("[DoSynchronized] 실행 : $isChecked") })
        )
    }

    @ObsoleteCoroutinesApi
    fun doSingleThread(isChecked: Boolean) {
        singleThreadRequestImpl.sendEventWithCoroutine(
            -1,
            SyncRequestEvent(func = { println("[DoSingleThread] 실행 : $isChecked") })
        )
    }

    @ObsoleteCoroutinesApi
    fun doActor() = actorRequestImpl.sendEvent()
}