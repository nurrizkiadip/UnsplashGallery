package com.nurrizkiadip.unsplashgallery.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.RemoteDataSource

class Repository(
	private val remoteData: RemoteDataSource
) : PhotoDataSource {
	override fun getPhotos(): LiveData<ApiResponse<List<Photo>>> {
		var photos = MutableLiveData<ApiResponse<List<Photo>>>()

		remoteData.getPhotos(object : RemoteDataSource.LoadPhotosCallback {
			override fun onPhotosReceived(photoResponse: MutableLiveData<ApiResponse<List<Photo>>>) {
				photos = photoResponse
			}
		})

		return photos
	}

	override fun getPhotoById(id: String): LiveData<ApiResponse<Photo>> {
		var photo = MutableLiveData<ApiResponse<Photo>>()

		remoteData.getPhotoById(id, object : RemoteDataSource.LoadPhotoCallback {
			override fun onPhotoReceived(photoResponse: MutableLiveData<ApiResponse<Photo>>) {
				photo = photoResponse
			}
		})

		return photo
	}
	companion object {
		@Volatile
		private var instance: Repository? = null

		fun getInstance(
			remoteData: RemoteDataSource,
		): Repository {
			return instance ?: synchronized(this) {
				instance ?: Repository(remoteData).apply { instance = this }
			}
		}
	}
}