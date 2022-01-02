package com.nurrizkiadip.unsplashgallery.data.source.remote

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.User
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiConfig
import com.nurrizkiadip.unsplashgallery.data.source.remote.network.ApiService
import com.nurrizkiadip.unsplashgallery.data.source.remote.response.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {

	fun getPhotos(callback: LoadPhotosCallback) {
		val photos = MutableLiveData<ApiResponse<List<Photo>>>()
		photos.postValue(LoadingResponse())

		apiService.getPhotos().enqueue(object : Callback<List<PhotoResponse>> {
			override fun onResponse(
				call: Call<List<PhotoResponse>>,
				response: Response<List<PhotoResponse>>
			) {
				if (response.isSuccessful) {
					val responsePhotos = response.body()

					if (responsePhotos!!.isNotEmpty()) {
						val list = ArrayList<Photo>()
						for (photo in responsePhotos) {
							list.add(
								Photo(
									id = photo.id,
									description = photo.description,
									regPhotoUrl = photo.urls?.regular,
									user = User(
										id = photo.user?.id.toString(),
										username = photo.user?.username,
										name = photo.user?.name,
										profileImageUrl = photo.user?.profileImage?.small,
									),
								)
							)
						}

						photos.postValue(SuccessResponse(list))
					} else {
						photos.postValue(EmptyResponse(listOf(), "No Photos Data"))
					}
				} else {
					photos.postValue(ErrorResponse("Failed to getting data"))
				}
			}

			override fun onFailure(call: Call<List<PhotoResponse>>, t: Throwable) {
				photos.postValue(ErrorResponse("Cannot getting data"))
			}
		})

		callback.onPhotosReceived(photos)
	}

	fun getPhotoById(id: String, callback: LoadPhotoCallback) {
		val photo = MutableLiveData<ApiResponse<Photo>>()
		photo.postValue(LoadingResponse())

		apiService.getPhotoById(id).enqueue(object : Callback<PhotoResponse> {
			override fun onResponse(
				call: Call<PhotoResponse>,
				response: Response<PhotoResponse>
			) {
				if (response.isSuccessful) {
					val responsePhoto = response.body()
					if (responsePhoto != null) {
						photo.postValue(
							SuccessResponse(
								Photo(
									id = responsePhoto.id,
									description = responsePhoto.description,
									regPhotoUrl = responsePhoto.urls?.regular,
									views = responsePhoto.views,
									user = User(
										id = responsePhoto.user?.id.toString(),
										username = responsePhoto.user?.username,
										name = responsePhoto.user?.name,
										profileImageUrl = responsePhoto.user?.profileImage?.medium,
									),
								)
							)
						)
					} else {
						photo.postValue(EmptyResponse(null, "No Photo Data"))
					}
				} else {
					photo.postValue(ErrorResponse("Failed to getting data"))
				}
			}

			override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
				photo.postValue(ErrorResponse("Cannot getting data"))
			}
		})

		callback.onPhotoReceived(photo)
	}

	interface LoadPhotosCallback {
		fun onPhotosReceived(photoResponse: MutableLiveData<ApiResponse<List<Photo>>>)
	}

	interface LoadPhotoCallback {
		fun onPhotoReceived(photoResponse: MutableLiveData<ApiResponse<Photo>>)
	}

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