package com.nurrizkiadip.unsplashgallery.data

import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteData: RemoteDataSource
) : PhotoDataSource {

    override suspend fun getPhotos(): Flow<ApiResponse<List<Photo>>> = remoteData.getPhotos()
    override suspend fun getPhotoById(id: String): Flow<ApiResponse<Photo>> = remoteData.getPhotoById(id)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(remoteData: RemoteDataSource): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository(remoteData).apply { instance = this }
            }
        }
    }
}