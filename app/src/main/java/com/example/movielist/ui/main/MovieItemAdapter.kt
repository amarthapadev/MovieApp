package com.example.movielist.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movielist.R
import com.example.movielist.ui.details.MovieDetailActivity
import com.example.movielist.ui.model.Movie
import java.text.SimpleDateFormat
import java.util.Locale

class MovieItemAdapter(private val dataSet: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieItemAdapter.ViewHolder>() {

    val humanReadableFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMovieName: TextView
        val tvReleaseDate: TextView
        val ivPoster: ImageView

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        init {
            // Define click listener for the ViewHolder's View
            tvMovieName = view.findViewById(R.id.tvMovieName)
            tvReleaseDate = view.findViewById(R.id.tvReleaseDate)
            ivPoster = view.findViewById(R.id.imageView)
        }

        fun bind(movie: Movie) {

            // Load the image using Glide or any other image loading library
            Glide.with(itemView.context)
                .load(movie.getPosterUrl())
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(ivPoster)

            tvMovieName.text = movie.name

            if (movie.releaseDate.isNotEmpty()) {

                val date = dateFormat.parse(movie.releaseDate)

                tvReleaseDate.text = date?.let { humanReadableFormat.format(it) }
            }

            itemView.setOnClickListener {

                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("position", adapterPosition)

                ActivityCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_movie, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val movie = dataSet[position]
        viewHolder.bind(movie)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
