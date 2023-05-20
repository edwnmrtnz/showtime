package com.github.edwnmrtnz.showtime.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.Movie
import com.google.android.material.imageview.ShapeableImageView

class MovieCastAdapter (private val cast : List<Movie.Cast>) : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("onBind")
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivCast = itemView.findViewById<ShapeableImageView>(R.id.ivCast)
    }

}