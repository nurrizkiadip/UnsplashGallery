package com.nurrizkiadip.unsplashgallery.data.source.remote

import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiConfig
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiService
import com.nurrizkiadip.unsplashgallery.utils.mapped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
	suspend fun getPhotos() = flow<ApiResponse<List<Photo>>> {
		emit(LoadingResponse())
		val response = apiService.getPhotos()
		if (response.isNotEmpty()) emit(SuccessResponse(response.mapped()))
		else emit(EmptyResponse(listOf(), "No Photo Data"))
	}.catch {
		emit(ErrorResponse("Cannot getting data"))
	}.flowOn(Dispatchers.IO)

	companion object {
		@Volatile
		private var instance: RemoteDataSource? = null
		fun getInstance(): RemoteDataSource {
			return instance ?: synchronized(this) {
				instance ?: RemoteDataSource(
					ApiConfig.provideApiService()
				).apply { instance = this }
			}
		}
	}
}