package com.nurrizkiadip.unsplashgallery.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import coil.load
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.databinding.ActivityDetailBinding
import com.nurrizkiadip.unsplashgallery.utils.gone
import com.nurrizkiadip.unsplashgallery.utils.visible

class DetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailBinding
	private lateinit var viewModel: DetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)
		viewModel = obtainViewModel()

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
			binding.imgPhotoDetail.load(it.regPhotoUrl)
			binding.userProfileImage.load(it.user.profileImageUrl)
			with(binding) {
				username.text = it.user.username
				name.text = it.user.name
				totalViewsPhoto.text = it.views.toString()
				tvDescriptionContent.text = it.description
			}
		}
	}

	private fun obtainViewModel(): DetailViewModel {
		val factory = DetailViewModelFactory.createFactory()
		return ViewModelProvider(this, factory)[DetailViewModel::class.java]
	}

	companion object {
		const val EXTRA_ID = "extra_id"
	}
}