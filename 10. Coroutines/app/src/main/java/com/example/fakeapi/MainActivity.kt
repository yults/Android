package com.example.fakeapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeapi.api.weak
import com.example.fakeapi.dao.PostDb
import com.example.fakeapi.dao.PostsDao
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import retrofit2.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var posts: ArrayList<Post> = arrayListOf()
    val realm: Realm = Realm.getDefaultInstance()
    private lateinit var postAdapter: PostsAdapter
    private val postsRealm = PostsDao()
    private val idSet = HashSet<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFromDB(realm)
        if (intent.extras != null && !intent.extras!!.isEmpty) {
            MyApp.instance.post.let {
                this.weak().run {
                    add(it)
                }
            }
        }
    }

    private fun downloadPostsApi() {
        progressBar.visibility = View.VISIBLE
        lifecycle.coroutineScope.launch {
            try {
                val response = MyApp.instance.apiService.downloadPosts()
                if (response.isSuccessful) {
                    posts = ArrayList(response.body()!!)
                    draw(posts)
                    progressBar.visibility = View.GONE
                    shortToast("loading post code: ${response.code()}")
                    updateDb(realm, posts.map { PostDb(it) })
                } else {
                    progressBar.visibility = View.GONE
                    shortToast("error")
                }
            } catch (t: Throwable) {
                progressBar.visibility = View.GONE
                shortToast(t.message.toString())
            }
        }

    }

    private fun makePostApi(post: Post) {
        lifecycle.coroutineScope.launch {
            try {
                val response = MyApp.instance.apiService.makePost(post)
                if (response.isSuccessful)
                    shortToast("posting post code: ${response.code()}")
                else
                    shortToast("error")
            } catch (t: Throwable) {
                shortToast(t.message.toString())
            }
        }
    }

    private fun deletePostApi(id: Int) {
        lifecycle.coroutineScope.launch {
            try {
                val response = MyApp.instance.apiService.deletePost(id)
                if (response.isSuccessful)
                    shortToast("deleting post code: ${response.code()}")
                else
                    shortToast("error")
            } catch (t: Throwable) {
                shortToast(t.message.toString())
            }
        }
    }

    private fun updateDb(realm: Realm, posts: List<PostDb>) {
        postsRealm.deleteAll(realm)
        postsRealm.makePosts(realm, posts)
    }

    private fun makePostDb(realm: Realm, postDb: PostDb) {
        postsRealm.makePost(realm, postDb)
        posts.add(Post(postDb))
        postAdapter.notifyItemInserted(posts.size - 1)
    }

    private fun deletePostDb(realm: Realm, id: Int) {
        val postDb = postsRealm.getPost(realm, id)
        postDb?.let {
            if (postDb.isNotEmpty()) {
                val post = postDb.first()
                val pos = posts.indexOf(Post(post!!))
                idSet.remove(post.id)
                postsRealm.deletePost(realm, postDb)
                posts.removeAt(pos)
                postAdapter.notifyItemRemoved(pos)
            }
        }
    }

    private fun loadFromDB(realm: Realm) {
        progressBar.visibility = View.VISIBLE
        val a = postsRealm.downloadPosts(realm)
        posts.clear()
        a.forEach {
            posts.add(Post(it))
            idSet.add(it.id)
        }
        draw(posts)
        progressBar.visibility = View.GONE
    }

    private fun draw(posts: ArrayList<Post>) {
        postAdapter = PostsAdapter(posts) { id ->
            this.weak().run {
                deletePostApi(id)
                deletePostDb(realm, id)
            }
        }

        myRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

    private fun add(post: Post) {
        post.id = findId()
        makePostApi(post)
        makePostDb(realm, PostDb(post))
    }

    private fun findId(): Int {
        var id = Random.nextInt(0, Int.MAX_VALUE)
        while (idSet.contains(id))
            id = Random.nextInt(0, Int.MAX_VALUE)
        idSet.add(id)
        return id
    }

    private fun shortToast(string: String) {
        Toast.makeText(
            this@MainActivity,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onAddClickEvent(view: View) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun onUpdateClickEvent(view: View) {
        this.weak().run {
            downloadPostsApi()
        }
    }

}