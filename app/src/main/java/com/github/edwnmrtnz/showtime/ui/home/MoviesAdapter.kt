package com.github.edwnmrtnz.showtime.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.Movie

class MoviesAdapter (private val onCLicked : (view : View, movie : Movie) -> Unit)
    : ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffUtiLCallback){

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
        val item = getItem(position)
        holder.ivImage.setImageResource(R.drawable.sample_item_movie)
        holder.itemView.setOnClickListener { onCLicked.invoke(holder.ivImage,item) }
        ViewCompat.setTransitionName(holder.ivImage, item.id.toString())

    }
}