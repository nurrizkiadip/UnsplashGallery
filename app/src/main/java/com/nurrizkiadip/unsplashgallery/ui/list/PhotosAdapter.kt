package com.nurrizkiadip.unsplashgallery.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.databinding.ItemPhotoBinding

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
				this.imgPhotos.load(photo.regPhotoUrl)
				this.profileImage.load(photo.user.profileImageUrl)
			}
		}

	}
}