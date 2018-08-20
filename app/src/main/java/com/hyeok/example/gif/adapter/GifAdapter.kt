package com.hyeok.example.gif.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.hyeok.example.gif.model.Gif

class GifAdapter(private val onClick : (Int) -> Unit) : PagedListAdapter<Gif, GifViewHolder>(diffCallback){

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Gif>(){
            override fun areItemsTheSame(oldItem: Gif?, newItem: Gif?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Gif?, newItem: Gif?): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder{
        return GifViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = getItem(position)
        gif?.let {
            holder.bindTo(gif)
        }.let {

        }
    }
}