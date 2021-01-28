package com.example.contactslist

import android.content.Context
import android.provider.ContactsContract

data class Contact(val name: String, val phoneNum: String)

fun Context.fetchAllContacts(): List<Contact> {
    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        .use { it ->
            if (it == null) return emptyList()
            val builder = ArrayList<Contact>()
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: "N/A"
                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: "N/A"
                builder.add(Contact(name, phoneNumber))
            }
            return builder
        }
}
