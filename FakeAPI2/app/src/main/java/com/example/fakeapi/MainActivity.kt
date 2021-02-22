package com.example.fakeapi

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var posts: List<Post>
    private  var addAs : AsyncT? = null
    private  var deleteAs : AsyncT? = null
    private  var refreshAs : AsyncT? =  null
    private  var downloadAs : GetAll? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        if (intent.extras != null && !intent.extras!!.isEmpty) {
            MyApp.instance.post?.let {
                add(it)
            }
        }
        downloadAs?.cancel(true)
        downloadAs = GetAll()
    }

    private inner class AsyncT(private val handler: () -> Unit) : AsyncTask<Unit, Unit, Unit>() {
        init {
            execute()
        }
        override fun doInBackground(vararg params: Unit) {
            handler()
        }
        override fun onPostExecute(result: Unit?) {
            GetAll()
            super.onPostExecute(result)
        }
    }
    private inner class GetAll : AsyncTask<Unit, Unit, List<Post>>() {
        init {
            execute()
        }
        override fun doInBackground(vararg params: Unit): List<Post>? {
            return MyApp.instance.postsDao?.getAll()
        }
        override fun onPostExecute(result: List<Post>) {
            if (result.isNotEmpty()) {
                progressBar?.visibility = ProgressBar.INVISIBLE
                posts = result
                fillRecyclerView()
            }
            else {
                MyApp.instance.apiService.downloadPosts().enqueue(DownloadCallback())
            }
            super.onPostExecute(result)
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
            !!! MyApp.instance.posts = response.body()
            fillRecyclerView()
        }
    }
    fun fillRecyclerView() {
        myRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostsAdapter(posts) {
                MyApp.instance.apiService.deletePost(it.id.toString()).enqueue(CallbackCode())
                deleteAs?.cancel(true)
                deleteAs = AsyncT {
                    MyApp.instance.postsDao?.deletePost(it)
                }
            }
        }
        changeVisibility()
    }
    fun changeVisibility() {
        myRecyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
    fun onAddClickEvent(view: View){
        val intent = Intent(this@MainActivity, AddPostActivity::class.java)
        startActivity(intent)
    }
    !!!
}