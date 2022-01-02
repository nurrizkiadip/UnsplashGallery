package com.nurrizkiadip.unsplashgallery.ui.list

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.source.remote.EmptyResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.ErrorResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.LoadingResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.SuccessResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: Repository
) : ViewModel() {
    val photos = MutableStateFlow<List<Photo>>(listOf())
    val isLoading = MutableStateFlow(false)
    val isEmpty = MutableStateFlow(false)
    val emptyMessage = MutableStateFlow("")

    fun getPhotos(context: Context) = viewModelScope.launch {
        repository.getPhotos().collect {
            when (it) {
                is SuccessResponse -> {
                    isLoading.value = false
                    isEmpty.value = false
                    it.body?.let { data -> photos.value = data }
                }
                is ErrorResponse -> {
                    isLoading.value = false
                    isEmpty.value = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is EmptyResponse -> {
                    isLoading.value = false
                    isEmpty.value = true
                    emptyMessage.value = it.body.toString()
                }
                is LoadingResponse -> {
                    isLoading.value = true
                }
            }
        }
    }
}