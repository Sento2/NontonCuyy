package com.Kel6.nontoncuyy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Kel6.nontoncuyy.R
import coil.load

class MovieAdapter(
    private var movies: List<Film>,
    private val onMovieClick: ((Film) -> Unit)? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view
        val ivPoster: ImageView = view.findViewById(R.id.ivPoster)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvInfo: TextView = view.findViewById(R.id.tvInfo)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvTitle.text = movie.judul
        holder.tvInfo.text = "${movie.tanggalRilis} • ${movie.kategori}"
        holder.tvRating.text = "★ ${movie.skorRating}"
        
        holder.ivPoster.load(movie.gambarPoster) {
            crossfade(true)
            placeholder(android.R.drawable.progress_horizontal)
            error(android.R.drawable.stat_notify_error)
        }

        holder.root.setOnClickListener {
            onMovieClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<Film>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}