package com.nurrizkiadip.unsplashgallery.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.source.remote.ApiResponse

class ListViewModel(
  private val repository: Repository
) : ViewModel() {
  fun getPhotos(): LiveData<ApiResponse<List<Photo>>> {
    return repository.getPhotos()
  }
}