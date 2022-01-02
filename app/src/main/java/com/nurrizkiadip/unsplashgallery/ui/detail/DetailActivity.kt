package com.nurrizkiadip.unsplashgallery.ui.detail

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nurrizkiadip.unsplashgallery.R
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.databinding.ActivityDetailBinding
import com.nurrizkiadip.unsplashgallery.ui.list.ListViewModelFactory
import com.nurrizkiadip.unsplashgallery.utils.gone
import com.nurrizkiadip.unsplashgallery.utils.visible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this)

        intent.getStringExtra(EXTRA_ID)?.let { id ->
            viewModel.getPhotoById(this, id)
            populatePhoto(viewModel.photo)
            populateProgressBar(viewModel.isLoading)
        }
    }

    private fun populateProgressBar(loading: MutableStateFlow<Boolean>) = lifecycleScope.launch {
        loading.collect { isLoading -> binding.pbDetail.let { if (isLoading) it.visible() else it.gone() } }
    }

    private fun populatePhoto(photo: MutableStateFlow<Photo>) = lifecycleScope.launch {
        photo.collect {
            Glide
                .with(this@DetailActivity)
                .load(it.regPhotoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_user)
                .into(binding.imgPhotoDetail)
            Glide
                .with(this@DetailActivity)
                .load(it.user.profileImageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_user)
                .into(binding.userProfileImage)

            with(binding) {
                username.text = it.user.username
                name.text = it.user.name
                totalViewsPhoto.text = it.views.toString()
                tvDescriptionContent.text = it.description
            }

        }
    }

    private fun obtainViewModel(activity: Activity): DetailViewModel {
        val factory = ListViewModelFactory.createFactory(activity)
        return ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}