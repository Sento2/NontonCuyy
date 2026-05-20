package com.Kel6.nontoncuyy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Kel6.nontoncuyy.R
import coil.load

class WatchlistAdapter(
    private var movies: List<Film>,
    private val onMovieClick: (Film) -> Unit,
    private val onRemoveClick: (Film) -> Unit
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    class WatchlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view
        val ivPoster: ImageView = view.findViewById(R.id.ivPoster)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvInfo: TextView = view.findViewById(R.id.tvInfo)
        val btnRemove: ImageView = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watchlist, parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvTitle.text = movie.judul
        holder.tvInfo.text = "${movie.tanggalRilis} • ${movie.kategori}"
        
        holder.ivPoster.load(movie.gambarPoster) {
            crossfade(true)
            placeholder(android.R.drawable.progress_horizontal)
            error(android.R.drawable.stat_notify_error)
        }

        holder.root.setOnClickListener { onMovieClick(movie) }
        holder.btnRemove.setOnClickListener { onRemoveClick(movie) }
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<Film>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}