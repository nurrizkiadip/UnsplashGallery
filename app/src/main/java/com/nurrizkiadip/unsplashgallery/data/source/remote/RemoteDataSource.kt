package com.nurrizkiadip.unsplashgallery.data.source.remote

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiConfig
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiService
import com.nurrizkiadip.unsplashgallery.data.source.remote.response.mapped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getPhotos() = flow<ApiResponse<List<Photo>>> {
        emit(LoadingResponse())
        val response = apiService.getPhotos()
        response.let {
            if (it.isNotEmpty()) emit(SuccessResponse(it.mapped()))
            else emit(EmptyResponse(listOf(), "No Photo Data"))
        }
    }.catch {
        emit(ErrorResponse("Cannot getting data"))
        Log.d(TAG, "getPhotos: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    fun getPhotoById(id: String) = flow<ApiResponse<Photo>> {
        val photo = MutableLiveData<ApiResponse<Photo>>()
        photo.postValue(LoadingResponse())
        emit(LoadingResponse())
        val response = apiService.getPhotoById(id)
        if (response.user != null) emit(SuccessResponse(response.mapped(1)))
        else emit(EmptyResponse(null, "No Photo Data"))
    }.catch {
        emit(ErrorResponse("Cannot getting data"))
        Log.d(TAG, "getPhotoById: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)

    companion object {
        private val TAG: String = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(application: Application): RemoteDataSource {
            return instance ?: synchronized(this) {
                instance ?: RemoteDataSource(
                    ApiConfig.getApiService(application)
                ).apply { instance = this }
            }
        }
    }
}