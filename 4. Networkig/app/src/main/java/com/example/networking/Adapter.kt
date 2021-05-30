package com.example.networking

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.image_view.view.*

class Adapter(
        private val list : List<Image>,
        private val onClick : (Image) -> Unit):
        RecyclerView.Adapter<Adapter.ViewHolder>() {
        class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
                fun bind(img: Image) {
                        with(root) {
                                Id.text = img.id.toString()
                                Author.text = img.author
                                Url.text = img.url
                        }
                }
        }
        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int) : ViewHolder {
                val viewHolder = ViewHolder(
                        LayoutInflater
                                .from(parent.context)
                                .inflate(R.layout.image_view, parent, false)
                )
                viewHolder.root.setOnClickListener {
                        onClick(list[viewHolder.adapterPosition])}
                return viewHolder
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])
        override fun getItemCount() = this.list.size
}
