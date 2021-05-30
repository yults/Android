package com.example.fakeapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeapi.api.weak
import com.example.fakeapi.dao.PostsDao
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var posts: ArrayList<Post> = arrayListOf()
    val realm: Realm = Realm.getDefaultInstance()
    var callL: Call<List<Post>>? = null
    var callP:Call<Post>? = null
    var callU:Call<Unit>? = null
    private lateinit var postAdapter: PostsAdapter
    private val postsRealm = PostsDao()
    private val idSet = HashSet<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFromDB(realm)
        if (intent.extras != null && !intent.extras!!.isEmpty) {
            MyApp.instance.post.let { add(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        callL?.cancel()
        callL = null
        callU?.cancel()
        callU = null
        callU?.cancel()
        callU = null
    }
    private fun downloadPostsApi() {
        progressBar.visibility = View.VISIBLE
        callL= MyApp.instance.apiService.downloadPosts()
        callL?.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                posts = ArrayList(response.body()!!)
                draw(posts)
                progressBar.visibility = View.GONE
                shortToast("loading post code: ${response.code()}")
                updateDb(realm, posts)
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                progressBar.visibility = View.GONE
                shortToast(t.message.toString())
            }
        })

    }

    private fun makePostApi(post: Post) {
        callP = MyApp.instance.apiService.makePost(post)
        callP?.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                shortToast("posting post code: ${response.code()}")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                shortToast(t.message.toString())
            }
        })

    }

    private fun deletePostApi(id: Int) {
        callU = MyApp.instance.apiService.deletePost(id)
        callU?.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                shortToast("deleting post code: ${response.code()}")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                shortToast(t.message.toString())
            }
        })
    }

    private fun updateDb(realm: Realm, posts: List<Post>) {
        postsRealm.deleteAll(realm)
        postsRealm.makePosts(realm, posts)
    }

    private fun makePostDb(realm: Realm, postDb: Post) {
        postsRealm.makePost(realm, postDb)
        posts.add(postDb)
        postAdapter.notifyItemInserted(posts.size - 1)
    }

    private fun deletePostDb(realm: Realm, id: Int) {
        val postDb = posts.firstOrNull { it.id == id }
        postDb?.let {
            val pos = posts.indexOf(postDb)
            idSet.remove(postDb.id)
            postsRealm.deletePost(realm, postDb)
            posts.removeAt(pos)
            postAdapter.notifyItemRemoved(pos)

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
        makePostDb(realm, post)
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
            this,
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