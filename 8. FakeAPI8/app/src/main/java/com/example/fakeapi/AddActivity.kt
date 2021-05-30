package com.example.fakeapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
    }
    fun addClickEvent(view : View) {
        if (newTitle.text.toString() == "" ) {
            Toast.makeText(this@AddActivity, "need more information", Toast.LENGTH_SHORT).show()
            return
        }
        MyApp.instance.post = Post(0, newTitle.text.toString(), newBody.text.toString(), 0)
        val intent = Intent(this@AddActivity, MainActivity::class.java)
        intent.putExtra("added", true)
        startActivity(intent)
    }
    fun backClickEvent(view: View) {
        val intent = Intent(this@AddActivity, MainActivity::class.java)
        startActivity(intent)
    }
}