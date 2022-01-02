package com.nurrizkiadip.unsplashgallery.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.nurrizkiadip.unsplashgallery.data.Photo

data class PhotoResponse(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("urls")
    val urls: Urls? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("views")
    val views: Int? = null
)

fun List<PhotoResponse>.mapped(): MutableList<Photo> {
    val listOfPhotos = mutableListOf<Photo>()
    this.forEach {
        listOfPhotos.add(it.mapped())
    }
    return listOfPhotos
}

fun PhotoResponse.mapped(size: Int? = 0): Photo {
    return Photo(
        id = this.id,
        description = this.description ?: "Tidak ada deskripsi gambar",
        regPhotoUrl = this.urls?.regular ?: "",
        views = this.views ?: 0,
        user = com.nurrizkiadip.unsplashgallery.data.User(
            id = this.user?.id.toString(),
            username = this.user?.username ?: "",
            name = this.user?.name ?: "Tidak diketahui",
            profileImageUrl = if (size == 0) this.user?.profileImage?.small
                ?: "" else this.user?.profileImage?.medium ?: "",
        ),
    )
}

data class Urls(

    @field:SerializedName("regular")
    val regular: String? = null,
)

data class User(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    )

data class ProfileImage(
    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("medium")
    val medium: String? = null
)
