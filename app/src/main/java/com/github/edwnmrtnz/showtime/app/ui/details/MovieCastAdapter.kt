package com.github.edwnmrtnz.showtime.app.ui.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.Movie
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MovieCastAdapter(
    private var cast: List<Movie.Cast>
) : RecyclerView.Adapter<MovieCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(cast[position].thumbnail)
            .into(holder.ivCast)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(cast: List<Movie.Cast>) {
        this.cast = cast
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCast = itemView.findViewById<ShapeableImageView>(R.id.ivCast)
    }
}
