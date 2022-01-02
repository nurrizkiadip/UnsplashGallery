package com.nurrizkiadip.unsplashgallery.data.source.remote.network

import com.nurrizkiadip.unsplashgallery.BuildConfig.CLIENT_ID
import com.nurrizkiadip.unsplashgallery.data.source.remote.response.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
	@GET("photos")
	suspend fun getPhotos(
		@Query("client_id") clientId: String = CLIENT_ID,
	): List<PhotoResponse>

	@GET("photos/{id}")
	suspend fun getPhotoById(
		@Path("id") id: String,
		@Query("client_id") clientId: String = CLIENT_ID,
	): PhotoResponse
}