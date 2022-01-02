package com.nurrizkiadip.unsplashgallery.data.source.remote.network

import com.nurrizkiadip.unsplashgallery.BuildConfig.CLIENT_ID
import com.nurrizkiadip.unsplashgallery.data.source.remote.response.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("photos")
  fun getPhotos(
    @Query("client_id") clientId: String = CLIENT_ID,
  ): Call<List<PhotoResponse>>

  @GET("photos/{id}")
  fun getPhotoById(
	  @Path("id") id: String,
	  @Query("client_id") clientId: String = CLIENT_ID,
  ): Call<PhotoResponse>
}