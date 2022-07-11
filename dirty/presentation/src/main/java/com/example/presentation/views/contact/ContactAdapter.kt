package com.example.presentation.views.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Contact
import com.example.presentation.databinding.ItemContactBinding

class ContactAdapter : RecyclerView.Adapter<ContactViewHolder>() {
    private val differ = AsyncListDiffer(this, ContactDiffCallback())

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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun updateItemList(list: List<Contact>) {
        differ.submitList(list)
    }
}

class ContactDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Contact) {
        with(binding) {
            tvName.text = item.name
            tvPhoneNumber.text = item.phoneNumber
        }
    }
}