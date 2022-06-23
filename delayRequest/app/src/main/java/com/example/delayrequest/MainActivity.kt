package com.example.delayrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.delayrequest.databinding.ActivityMainBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val actor by lazy { vm.doActor() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setListener()
        setActor()
    }

    private fun setListener() {
        with(binding) {
            cbSynchronized.setOnCheckedChangeListener { _, isChecked ->
                vm.doSynchronized(isChecked)
            }

            cbCoroutine.setOnCheckedChangeListener { _, isChecked ->
                vm.doMutex(isChecked)
            }

            cbConcurrent.setOnCheckedChangeListener { _, isChecked ->
                vm.doConcurrent(isChecked)
            }

            cbSingleThread.setOnCheckedChangeListener { _, isChecked ->
                vm.doSingleThread(isChecked)
            }

            cbActor.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    actor.send(InJob(-1, isChecked))
                }
            }
        }
    }

    private fun setActor() {
        lifecycleScope.launch {
            val actorResponse = CompletableDeferred<Job?>()
            actor.send(DoJob(actorResponse))
            actorResponse.await()
        }
    }
}