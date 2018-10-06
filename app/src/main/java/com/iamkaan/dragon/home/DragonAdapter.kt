package com.iamkaan.dragon.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.iamkaan.dragon.R
import com.squareup.picasso.Picasso
import java.util.*

class DragonAdapter(private val viewModels: ArrayList<DragonViewModel> = arrayListOf()) : RecyclerView.Adapter<DragonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragonViewHolder {
        return DragonViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DragonViewHolder, position: Int) {
        holder.bind(viewModels[position])
    }

    override fun getItemCount() = viewModels.size

    fun updateViewModels(viewModels: List<DragonViewModel>) {
        this.viewModels.clear()
        this.viewModels.addAll(viewModels)
        notifyDataSetChanged()
    }
}

class DragonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val photo: ImageView = itemView.findViewById(R.id.photo)
    val name: TextView = itemView.findViewById(R.id.name)
    val likes: TextView = itemView.findViewById(R.id.likes)
    val time: TextView = itemView.findViewById(R.id.time)
    val bin: ImageView = itemView.findViewById(R.id.bin)

    fun bind(viewModel: DragonViewModel) {
        Picasso.get()
                .load(viewModel.imageUrl)
                .into(photo)
        name.text = viewModel.name
        time.text = viewModel.time
        bin.visibility = if (viewModel.deletable) View.VISIBLE else View.INVISIBLE
        likes.text = viewModel.likes.toString()
        likes.setCompoundDrawablesWithIntrinsicBounds(likeDrawable(likes.resources, viewModel), null, null, null)
        likes.setOnClickListener {
            likes.isEnabled = false
            viewModel.onLikeClick {
                likes.isEnabled = true
            }
        }
        bin.setOnClickListener {
            bin.isEnabled = false
            viewModel.onDeleteClick {
                bin.isEnabled = true
            }
        }
    }

    private fun likeDrawable(resources: Resources, viewModel: DragonViewModel) = if (viewModel.liked)
        ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null)
    else
        ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_outline, null)
}

data class DragonViewModel(
        val name: String,
        val imageUrl: String,
        val time: String,
        val deletable: Boolean,
        val liked: Boolean,
        val likes: Int,
        val onLikeClick: (onComplete: () -> Unit) -> Unit,
        val onDeleteClick: (onComplete: () -> Unit) -> Unit
)
