package com.example.presentation.views.contact

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Contact
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ext.repeatOnStart
import com.example.presentation.ext.visibilityExt
import com.example.presentation.model.ContactAction
import com.example.presentation.model.Events
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: ContactViewModel by viewModels()

    private val contactAdapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        repeatOnStart {
            launch {
                vm.eventFlow.collect { handleEvent(it) }
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvContact.layoutManager = LinearLayoutManager(this@MainActivity)
            rvContact.adapter = contactAdapter.apply {
                setClickListener { contact, contactAction ->
                    vm.contactAction(contact, contactAction)
                }
            }

            tvSubmit.setOnClickListener {
                vm.contactAction(
                    Contact(
                        name = etName.text.toString(),
                        phoneNumber = etPhone.text.toString()
                    ),
                    ContactAction.CREATE
                )
            }
        }
    }

    private fun handleEvent(events: Events) {
        when (val event = events as ContactViewModel.ContactEvents) {
            is ContactViewModel.ContactEvents.LoadAllContact -> {
                val listIsEmpty = event.contactList.isNotEmpty()
                contactListVisibility(listIsEmpty)
                if (listIsEmpty) contactAdapter.updateItemList(event.contactList)
            }
        }
    }

    private fun contactListVisibility(status: Boolean) {
        with(binding) {
            tvContactEmpty.visibilityExt(!status)
            rvContact.visibilityExt(status)
        }
    }
}