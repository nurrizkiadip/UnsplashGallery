package com.nurrizkiadip.unsplashgallery.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse

class DetailViewModel(
  private val repository: Repository
) : ViewModel() {
	fun getPhotoById(id: String): LiveData<ApiResponse<Photo>> {
		return repository.getPhotoById(id)
	}
}