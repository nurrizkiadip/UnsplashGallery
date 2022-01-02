package com.nurrizkiadip.unsplashgallery.utils

import android.view.View
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.User
import com.nurrizkiadip.unsplashgallery.data.source.remote.response.PhotoResponse

fun View.visible() {
	this.visibility = View.VISIBLE
}

fun View.gone() {
	visibility = View.GONE
}

fun List<PhotoResponse>.mapped(): MutableList<Photo> {
	val listOfPhotos = mutableListOf<Photo>()
	this.forEach { listOfPhotos.add(it.mapped()) }
	return listOfPhotos
}

fun PhotoResponse.mapped(size: Int? = 0): Photo {
	return Photo(
		id = this.id,
		description = this.description ?: "Tidak ada deskripsi gambar",
		regPhotoUrl = this.urls?.regular ?: "",
		views = this.views ?: 0,
		user = User(
			id = this.user?.id.toString(),
			username = this.user?.username ?: "",
			name = this.user?.name ?: "Tidak diketahui",
			profileImageUrl = if (size == 0) this.user?.profileImage?.small ?: ""
			else this.user?.profileImage?.medium ?: "",
		),
	)
}
