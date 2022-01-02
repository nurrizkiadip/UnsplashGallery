package com.nurrizkiadip.unsplashgallery.data

import androidx.lifecycle.LiveData
import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse

interface PhotoDataSource {
  fun getPhotos(): LiveData<ApiResponse<List<Photo>>>
  fun getPhotoById(id: String): LiveData<ApiResponse<Photo>>
}