package com.example.movielist.ui.details

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movielist.R
import com.example.movielist.ui.model.Credit
import com.facebook.shimmer.ShimmerFrameLayout

class CreditsAdapter(private val dataSet: ArrayList<Credit>) :
    RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCreditName: TextView
        val tvCreditCharacter: TextView
        val ivCreditProfileImage: ImageView
        val shimmerFrameLayout: ShimmerFrameLayout

        init {

            tvCreditName = view.findViewById(R.id.tvCreditName)
            tvCreditCharacter = view.findViewById(R.id.tvCreditCharacter)
            ivCreditProfileImage = view.findViewById(R.id.ivCreditProfileImage)
            shimmerFrameLayout = view.findViewById(R.id.shimmerLayout)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_credit, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvCreditName.text = dataSet[position].name
        viewHolder.tvCreditCharacter.text = dataSet[position].characterName

        Glide.with(viewHolder.itemView.context)
            .load(dataSet[position].getProfilePicUrl())
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
            .into(viewHolder.ivCreditProfileImage)

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
