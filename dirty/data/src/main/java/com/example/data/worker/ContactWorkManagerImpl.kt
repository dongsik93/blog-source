package com.example.data.worker

import android.content.Context
import androidx.work.*
import com.example.domain.worker.ContactWorkManager
import javax.inject.Inject

class ContactWorkManagerImpl @Inject constructor(
    context: Context,
): ContactWorkManager {
    private val workManager = WorkManager.getInstance(context)

    override fun start() {
        println("Worker Start !")

        val constraints = Constraints.Builder()
            /* 네트워크 연결 */
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(ContactWorker::class.java)

        request.setConstraints(constraints)
        request.setInputData(createInputParams(1))

        workManager.beginUniqueWork(WORK_ID, ExistingWorkPolicy.KEEP, request.build())
            .enqueue()
    }

    private fun createInputParams(accountId: Long): Data {
        return Data.Builder().apply {
            if (accountId > 0L) {
                putLong(PARAMS_ACCOUNT_ID, accountId)
            }
        }.build()
    }

    companion object {
        private const val WORK_ID = "ContactDirtySync"
        const val PARAMS_ACCOUNT_ID = "account_id"
    }
}