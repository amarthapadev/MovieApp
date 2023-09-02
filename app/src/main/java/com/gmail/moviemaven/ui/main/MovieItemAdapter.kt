package com.gmail.moviemaven.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gmail.moviemaven.R
import com.gmail.moviemaven.ui.details.MovieDetailActivity
import com.gmail.moviemaven.ui.model.Movie
import com.facebook.shimmer.ShimmerFrameLayout
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
        val shimmerLayout: ShimmerFrameLayout

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        init {
            // Define click listener for the ViewHolder's View
            tvMovieName = view.findViewById(R.id.tvMovieName)
            tvReleaseDate = view.findViewById(R.id.tvReleaseDate)
            ivPoster = view.findViewById(R.id.imageView)
            shimmerLayout = view.findViewById(R.id.shimmerLayoutPoster)
        }

        fun bind(movie: Movie) {

            Glide.with(itemView.context)
                .load(movie.getPosterUrl())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        shimmerLayout.stopShimmer() // Stop the shimmer animation if the image loading fails
                        shimmerLayout.setShimmer(null) // Optionally, clear the shimmer effect completely
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        shimmerLayout.stopShimmer() // Stop the shimmer animation when the image is loaded
                        shimmerLayout.setShimmer(null) // Optionally, clear the shimmer effect completely
                        return false
                    }
                })
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
