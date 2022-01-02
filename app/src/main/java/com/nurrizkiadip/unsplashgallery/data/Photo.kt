package com.nurrizkiadip.unsplashgallery.data

data class Photo(
  var id: String,
  var views: Int? = null,
  var description: String? = null,
  var regPhotoUrl: String? = null,
  var user: User? = null,
)
