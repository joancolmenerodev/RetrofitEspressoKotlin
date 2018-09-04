package com.joancolmenerodev.retrofitespresso.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joancolmenerodev.retrofitespresso.R
import kotlinx.android.synthetic.main.author_item.view.*

class AuthorsAdapter(var context: Context, var list: List<String>) : RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.author_item, parent, false)
        return ViewHolder(v)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvAuthor.text = "Name : " + list[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(list[position])
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor = itemView.tv_author
    }
}