package com.github.edwnmrtnz.showtime.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.Movie

class SectionMoviesAdapter(
    private val onClicked: (view : View, Movie) -> Unit
) : ListAdapter<SectionedMovie, SectionMoviesAdapter.ViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<SectionedMovie>() {
        override fun areItemsTheSame(
            oldItem: SectionedMovie,
            newItem: SectionedMovie
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SectionedMovie,
            newItem: SectionedMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_sectioned_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvSection.text = item.section

        holder.rvMovies.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.rvMovies.setHasFixedSize(true)
        val adapter = MoviesAdapter(onClicked)
        adapter.submitList(item.movies)
        holder.rvMovies.adapter = adapter
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSection : AppCompatTextView = itemView.findViewById(R.id.tvSection)
        val rvMovies : RecyclerView = itemView.findViewById(R.id.rvMovies)
    }

}