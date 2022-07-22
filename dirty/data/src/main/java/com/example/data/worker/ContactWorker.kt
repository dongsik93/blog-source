package com.example.data.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.db.AppDataBase
import com.example.data.repository.DirtyFlagRepositoryImpl
import com.example.data.worker.ContactWorkManagerImpl.Companion.PARAMS_ACCOUNT_ID

class ContactWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val accountId = inputData.getLong(PARAMS_ACCOUNT_ID, -1L)
        println("Contact Worker : $accountId")

        val context = applicationContext
        val database = AppDataBase.getInstance(context)
        val repository = DirtyFlagRepositoryImpl(database.dirtyFlagDao, database.contactDao)

        val result = repository.syncDirty()

        return if (result) Result.success() else Result.failure()
    }
}