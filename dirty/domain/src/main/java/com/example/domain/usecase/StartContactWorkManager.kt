package com.example.domain.usecase

import com.example.domain.worker.ContactWorkManager
import javax.inject.Inject

class StartContactWorkManager @Inject constructor(
    private val workManager: ContactWorkManager
) {
    operator fun invoke() {
        workManager.start()
    }
}