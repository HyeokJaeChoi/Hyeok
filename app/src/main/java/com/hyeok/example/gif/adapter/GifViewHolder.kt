package com.hyeok.example.gif.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class GifViewHolder(parent : ViewGroup, private val onClick : (Int) -> Unit) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate())