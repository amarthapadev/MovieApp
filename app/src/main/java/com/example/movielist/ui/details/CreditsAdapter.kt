package com.example.movielist.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.ui.model.Credit

class CreditsAdapter(private val dataSet: ArrayList<Credit>) :
    RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCreditName: TextView
        val tvCreditCharacter: TextView
        val ivCreditProfileImage: ImageView

        init {

            tvCreditName = view.findViewById(R.id.tvCreditName)
            tvCreditCharacter = view.findViewById(R.id.tvCreditCharacter)
            ivCreditProfileImage = view.findViewById(R.id.ivCreditProfileImage)
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
