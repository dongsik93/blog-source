package com.example.delayrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.delayrequest.databinding.ActivityMainBinding
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setListener()
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
        }
    }
}