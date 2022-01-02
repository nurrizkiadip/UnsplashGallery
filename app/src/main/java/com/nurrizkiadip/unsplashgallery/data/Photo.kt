package com.nurrizkiadip.unsplashgallery.data

data class Photo(
    var id: String,
    var views: Int,
    var description: String,
    var regPhotoUrl: String,
    var user: User,
)
