package com.example.fakeapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post.view.*

class PostsAdapter(
    private val posts : List<Post>,
    private val onClick : (Post) -> Unit
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        )
        holder.root.del.setOnClickListener {
            onClick(posts[holder.adapterPosition])
        }
        return holder
    }

    class PostViewHolder(val root : View) : RecyclerView.ViewHolder(root) {
        fun bind(post : Post) {
            with(root) {
                title.text = post.title
                body.text = post.body
            }
        }
    }
    override fun getItemCount() = this.posts.size
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(posts[position])
}