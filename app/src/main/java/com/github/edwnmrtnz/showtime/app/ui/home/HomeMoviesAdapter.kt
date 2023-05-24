package com.github.edwnmrtnz.showtime.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.squareup.picasso.Picasso

class HomeMoviesAdapter(private val onCLicked: (view: View, movie: MoviePreview) -> Unit) :
    ListAdapter<MoviePreview, HomeMoviesAdapter.ViewHolder>(DiffUtiLCallback) {

    object DiffUtiLCallback : DiffUtil.ItemCallback<MoviePreview>() {
        override fun areItemsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: AppCompatImageView = itemView.findViewById(R.id.ivMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        ViewCompat.setTransitionName(holder.ivImage, item.id.toString())
        holder.itemView.setOnClickListener { onCLicked.invoke(holder.ivImage, item) }
        Picasso.get()
            .load(item.thumbnail)
            .into(holder.ivImage)
    }
}
