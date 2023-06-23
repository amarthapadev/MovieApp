package com.example.movielist.ui.details

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.ui.model.SimilarMovie

class SimilarMoviesAdapter(private val dataSet: ArrayList<SimilarMovie>) :
    RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMoviePoster: ImageView

        init {

            ivMoviePoster = view.findViewById(R.id.ivMoviePoster)
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
