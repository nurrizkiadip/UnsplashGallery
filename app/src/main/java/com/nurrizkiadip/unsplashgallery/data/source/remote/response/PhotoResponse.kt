package com.nurrizkiadip.unsplashgallery.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
	@field:SerializedName("id")
	val id: String,
	@field:SerializedName("urls")
	val urls: Urls? = null,
	@field:SerializedName("user")
	val user: User? = null,
)

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
)

data class ProfileImage(
	@field:SerializedName("small")
	val small: String? = null,
)
