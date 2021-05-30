package com.example.contactslist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val userPermission : Int = 404
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            userPermission -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showContacts()
                } else {
                    Toast.makeText(
                            this,
                            R.string.permission_error,
                            Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    private fun showContacts(){
        val viewManager = LinearLayoutManager(this)
        val contacts = fetchAllContacts()
        Toast.makeText(
                this,
                resources.getQuantityString(R.plurals.contact_toast, contacts.size, contacts.size),
                Toast.LENGTH_SHORT
        ).show()

        myRecyclerView.apply {
            layoutManager = viewManager
            adapter = ContactAdapter(contacts) {
                val intent = Intent(Intent.ACTION_DIAL)
                with(intent) {
                    data = Uri.parse("tel:${it.phoneNum}")
                }
                startActivity(intent)
            }
        }
    }

    fun onClickEvent(view: View?) {
        if (view is Button) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), userPermission)
            }
            else {
                showContacts()
            }
        }
    }
}