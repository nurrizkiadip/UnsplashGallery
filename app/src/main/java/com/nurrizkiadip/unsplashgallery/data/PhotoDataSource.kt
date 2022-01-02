package com.nurrizkiadip.unsplashgallery.data

import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse
import kotlinx.coroutines.flow.Flow

interface PhotoDataSource {
    suspend fun getPhotos(): Flow<ApiResponse<List<Photo>>>
    suspend fun getPhotoById(id: String): Flow<ApiResponse<Photo>>
}