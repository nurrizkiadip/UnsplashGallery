package com.nurrizkiadip.unsplashgallery.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurrizkiadip.unsplashgallery.data.Photo
import com.nurrizkiadip.unsplashgallery.databinding.ActivityListBinding
import com.nurrizkiadip.unsplashgallery.utils.gone
import com.nurrizkiadip.unsplashgallery.utils.visible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
	private lateinit var binding: ActivityListBinding
	private lateinit var viewModel: ListViewModel
	private lateinit var photosAdapter: PhotosAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityListBinding.inflate(layoutInflater)
		setContentView(binding.root)

		viewModel = obtainViewModel()
		initPhotosAdapter()

		viewModel.getPhotos(this)
		populateEmptyView(viewModel.isEmpty)
		populateLoadingView(viewModel.isLoading)
		populateEmptyMessage(viewModel.emptyMessage)
		populatePhotos(viewModel.photos)
	}

	private fun populatePhotos(photos: MutableStateFlow<List<Photo>>) =
		lifecycleScope.launch { photos.collect { photosAdapter.setPhotos(it) } }

	private fun populateEmptyMessage(message: MutableStateFlow<String>) = lifecycleScope.launch {
		message.collect { if (it.isNotBlank()) binding.tvEmptyList.text = it }
	}

	private fun populateLoadingView(isLoading: MutableStateFlow<Boolean>) = lifecycleScope.launch {
		isLoading.collect { loading -> binding.pbList.let { if (loading) it.visible() else it.gone() } }
	}

	private fun populateEmptyView(isEmpty: MutableStateFlow<Boolean>) = lifecycleScope.launch {
		isEmpty.collect { empty -> binding.tvEmptyList.let { if (empty) it.visible() else it.gone() } }
	}

	private fun initPhotosAdapter() {
		photosAdapter = PhotosAdapter()
		binding.rvPhotos.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = this@ListActivity.photosAdapter
		}
	}

	private fun obtainViewModel(): ListViewModel {
		val factory = ListViewModelFactory.createFactory()
		return ViewModelProvider(this, factory)[ListViewModel::class.java]
	}
}