package com.nurrizkiadip.unsplashgallery.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.User
import com.nurrizkiadip.unsplashgallery.data.source.remote.EmptyResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.ErrorResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.LoadingResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.SuccessResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {

    val isLoading = MutableStateFlow(false)
    val photo = MutableStateFlow(Photo("", 0, "", "", User("", "", "", "")))

    fun getPhotoById(context: Context, id: String) = viewModelScope.launch {
        repository.getPhotoById(id).collect {
            when (it) {
                is SuccessResponse -> {
                    isLoading.value = false
                    it.body?.let { data ->
                        println("DataDetail = $data")
                        photo.value = data
                    }
                }
                is ErrorResponse -> {
                    isLoading.value = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is EmptyResponse -> {
                    isLoading.value = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is LoadingResponse -> {
                    isLoading.value = true
                }
            }
        }
    }
}