package com.hyeok.example.gif.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hyeok.example.R
import com.hyeok.example.gif.model.Gif

class GifViewHolder(parent : ViewGroup, private val onClick : (Int) -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.gif, parent, false)){

    private val gifView = itemView.findViewById<ImageView>(R.id.gif_image)

    fun bindTo(gif : Gif?){
        Glide.with(itemView)
                .asGif()
                .load(gif?.fileUrl)
                .into(gifView)
        itemView.setOnClickListener {
            gif?.let {
                onClick(it.id)
            }
        }
    }
}