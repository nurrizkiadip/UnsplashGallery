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

fun PhotoResponse.mapped(): Photo {
	return Photo(
		id = this.id,
		regPhotoUrl = this.urls?.regular ?: "",
		user = User(
			id = this.user?.id.toString(),
			name = this.user?.name ?: "Tidak diketahui",
			profileImageUrl = this.user?.profileImage?.small ?: "",
		),
	)
}
