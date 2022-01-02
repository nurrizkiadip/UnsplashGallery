package com.nurrizkiadip.unsplashgallery.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.databinding.ItemPhotoBinding
import com.nurrizkiadip.unsplashgallery.ui.detail.DetailActivity

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val listPhotos = ArrayList<Photo>()

    fun setPhotos(listPhotos: List<Photo>?) {
        if (listPhotos.isNullOrEmpty()) return

        this.listPhotos.clear()
        this.listPhotos.addAll(listPhotos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = listPhotos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listPhotos.size

    inner class ViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            with(binding) {
                this.name.text = photo.user.name

                Glide
                    .with(itemView.context)
                    .load(photo.regPhotoUrl)
                    .centerCrop()
                    .into(this.imgPhotos)
                Glide
                    .with(itemView.context)
                    .load(photo.user.profileImageUrl)
                    .centerCrop()
                    .into(this.profileImage)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, photo.id)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }
}