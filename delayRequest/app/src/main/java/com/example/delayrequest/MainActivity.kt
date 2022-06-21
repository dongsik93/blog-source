package com.example.delayrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.delayrequest.databinding.ActivityMainBinding

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
            cbSynchronized.setOnClickListener {
                vm.doSynchronized(cbSynchronized.isChecked)
            }

            cbCoroutine.setOnClickListener {
                vm.doMutex(cbSynchronized.isChecked)
            }

            cbConcurrent.setOnClickListener {
                vm.doConcurrent(cbSynchronized.isChecked)
            }
        }
    }
}