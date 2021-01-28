package com.example.fakeapi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)
    }
    fun addClickEvent() {
        if (newId.text.toString() == "" || newTitle.text.toString() == "" || newuserId.text.toString() == "") {
            Toast.makeText(this@AddActivity, "Need more information", Toast.LENGTH_SHORT).show()
            return
        }
        MyApp.instance.post = Post(newId.text.toString().toInt(), newTitle.text.toString(), newBody.text.toString(), newuserId.text.toString().toInt())
        val intent = Intent(this@AddActivity, MainActivity::class.java)
        intent.putExtra("Added", true)
        startActivity(intent)
    }
    fun onBackClickEvent() {
        val intent = Intent(this@AddActivity, MainActivity::class.java)
        startActivity(intent)
    }
}