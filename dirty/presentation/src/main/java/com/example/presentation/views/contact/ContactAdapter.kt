package com.example.presentation.views.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Contact
import com.example.presentation.databinding.ItemContactBinding
import com.example.presentation.ext.visibilityExt
import com.example.presentation.model.ContactAction

class ContactAdapter : RecyclerView.Adapter<ContactViewHolder>() {
    private val differ = AsyncListDiffer(this, ContactDiffCallback())

    private var clickListener: (Contact, ContactAction) -> Unit = { _, _ -> }

    fun setClickListener(func: (Contact, ContactAction) -> Unit) {
        clickListener = func
    }

    fun updateItemList(list: List<Contact>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(differ.currentList[position], clickListener)
    }

    override fun getItemCount() = differ.currentList.size
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Contact, clickListener: (Contact, ContactAction) -> Unit) {
        with(binding) {
            etName.setText(item.name)
            etPhone.setText(item.phoneNumber)

            tvEdit.setOnClickListener {
                tvConfirm.visibilityExt(true)
                etName.isFocusableInTouchMode = true
                etName.requestFocus()
                etPhone.isFocusableInTouchMode = true
            }
            tvDelete.setOnClickListener { clickListener.invoke(item, ContactAction.DELETE) }

            tvConfirm.setOnClickListener {
                clickListener.invoke(
                    Contact(
                        etName.text.toString(),
                        etPhone.text.toString()
                    ),
                    ContactAction.UPDATE
                )
            }
        }
    }
}