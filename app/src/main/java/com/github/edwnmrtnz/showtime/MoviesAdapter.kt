package com.github.edwnmrtnz.showtime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter (private val onCLicked : (movie : Movie) -> Unit) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffUtiLCallback){

    object DiffUtiLCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivImage : AppCompatImageView = itemView.findViewById(R.id.ivMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage.setImageResource(R.drawable.sample_item_movie)
        holder.itemView.setOnClickListener { onCLicked.invoke(getItem(position)) }
    }
}