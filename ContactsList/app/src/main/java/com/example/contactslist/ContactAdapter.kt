package com.example.contactslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*

class ContactAdapter(
    private val contacts : List<Contact>,
    private val onClick : (Contact) -> Unit
): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        )
        holder.root.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        return holder;
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(contacts[position])

    class ContactViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(contact: Contact) {
            with(root) {
                name.text = contact.name
                phone_number.text = contact.phoneNum
            }
        }
    }

    override fun getItemCount() = contacts.size
}
