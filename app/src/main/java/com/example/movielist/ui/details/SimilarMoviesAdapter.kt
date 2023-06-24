package com.example.movielist.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movielist.R
import com.example.movielist.ui.model.SimilarMovie
import com.facebook.shimmer.ShimmerFrameLayout

class SimilarMoviesAdapter(private val dataSet: ArrayList<SimilarMovie>) :
    RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMoviePoster: ImageView
        val shimmerFrameLayout: ShimmerFrameLayout

        init {

            ivMoviePoster = view.findViewById(R.id.ivMoviePoster)
            shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_similar_movie, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.setOnClickListener {

            val intent = Intent(viewHolder.itemView.context, MovieDetailActivity::class.java)
            intent.putExtra("movieId", dataSet[position].id)

            ActivityCompat.startActivity(viewHolder.itemView.context, intent, null)
        }

        Glide.with(viewHolder.itemView.context)
            .load(dataSet[position].getPosterPath())
            .placeholder(R.drawable.ic_launcher_background)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewHolder.shimmerFrameLayout.stopShimmer() // Stop the shimmer animation if the image loading fails
                    viewHolder.shimmerFrameLayout.setShimmer(null) // Optionally, clear the shimmer effect completely
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewHolder.shimmerFrameLayout.stopShimmer() // Stop the shimmer animation when the image is loaded
                    viewHolder.shimmerFrameLayout.setShimmer(null) // Optionally, clear the shimmer effect completely
                    return false
                }
            })
            .into(viewHolder.ivMoviePoster)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    /*
    DiffUtil example
    @Pokedex app by Skydoves
        companion object {
            private val diffUtil = object : DiffUtil.ItemCallback<Pokemon>() {

                override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                    oldItem.name == newItem.name

                override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                    oldItem == newItem
            }
        }
     */


}
