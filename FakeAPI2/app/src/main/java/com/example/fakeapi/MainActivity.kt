package com.example.fakeapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val args = intent.extras
        if (args != null && !args.isEmpty) {
                MyApp.instance.post?.let {
                    add(it)
                }
        }
        if (MyApp.instance.dao?.getAll()?.isNotEmpty()!!) {
            fillRecyclerView()
        }
        else {
            MyApp.instance.apiService.downloadPosts().enqueue(DownloadCallback())
        }
    }
    inner class CallbackCode : Callback<Post> {
        override fun onFailure(call: Call<Post>?, throwable: Throwable?) {
            Toast.makeText(this@MainActivity, "Problems with connection", Toast.LENGTH_SHORT).show()
        }
        override fun onResponse(call: Call<Post>?, response: Response<Post>?) {
            if (response != null) {
                Toast.makeText(this@MainActivity, "Finished with ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    inner class DownloadCallback : Callback<List<Post>> {
        override fun onFailure(call: Call<List<Post>>, throwable: Throwable) {
            Toast.makeText(this@MainActivity, "Problems with connection", Toast.LENGTH_SHORT).show()
            changeVisibility()
        }
        override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
            val newPosts = response.body()
            if (newPosts!= null) {
                MyApp.instance.clearData()
                for (post in newPosts) {
                    MyApp.instance.dao?.insertAll(post)
                }
            }
            fillRecyclerView()
        }
    }

    fun fillRecyclerView() {
        myRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyApp.instance.dao?.getAll()?.let { it ->
                PostsAdapter(it) {
                    MyApp.instance.apiService.deletePost(it.id.toString()).enqueue(CallbackCode())
                    MyApp.instance.dao?.deletePost(it)
                    fillRecyclerView()
                }
            }
        }
        changeVisibility()
    }
    private fun add(post: Post) {
        MyApp.instance.apiService.makePost(post).enqueue(CallbackCode())
        MyApp.instance.dao?.insertAll(post)
        fillRecyclerView()
    }
    fun onReloadClickEvent(view: View) {
        changeVisibility()
        MyApp.instance.apiService.downloadPosts().enqueue(DownloadCallback())
    }
    fun onAddClickEvent(view: View) {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivity(intent)
    }
    fun changeVisibility() {
        myRecyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}