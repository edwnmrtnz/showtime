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
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie
import com.github.edwnmrtnz.showtime.ui.helpers.ScrollStateHolder

// Saving nested recyclerview state
// https://rubensousa.com/2019/08/27/saving_scroll_state_of_nested_recyclerviews/
class SectionMoviesAdapter(
    private val scrollStateHolder: ScrollStateHolder,
    private val onClicked: (view: View, MoviePreview) -> Unit
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
        val vh = ViewHolder(view, onClicked, scrollStateHolder)
        vh.onCreated()
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    class ViewHolder(
        itemView: View,
        onClicked: (view: View, MoviePreview) -> Unit,
        private val scrollStateHolder: ScrollStateHolder
    ) : RecyclerView.ViewHolder(itemView), ScrollStateHolder.ScrollStateKeyProvider {
        private val tvSection: AppCompatTextView = itemView.findViewById(R.id.tvSection)
        private val rvMovies: RecyclerView = itemView.findViewById(R.id.rvMovies)
        private val layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        private val adapter = HomeMoviesAdapter(onClicked)
        var currentItem: SectionedMovie? = null

        fun bind(item: SectionedMovie) {
            this.currentItem = item
            tvSection.text = item.section
            adapter.submitList(item.movies)
            scrollStateHolder.restoreScrollState(rvMovies, this)
        }

        fun onCreated() {
            rvMovies.layoutManager = layoutManager
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = adapter
            scrollStateHolder.setupRecyclerView(rvMovies, this)
        }

        override fun getScrollStateKey(): String? = currentItem?.section

        fun onRecycled() {
            scrollStateHolder.saveScrollState(rvMovies, this)
            currentItem = null
        }
    }
}
