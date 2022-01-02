package com.nurrizkiadip.unsplashgallery.ui.list

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurrizkiadip.unsplashgallery.data.source.remote.EmptyResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.ErrorResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.LoadingResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.SuccessResponse
import com.nurrizkiadip.unsplashgallery.databinding.ActivityListBinding
import com.nurrizkiadip.unsplashgallery.utils.gone
import com.nurrizkiadip.unsplashgallery.utils.visible

class ListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityListBinding
  private lateinit var viewModel: ListViewModel
  private lateinit var photosAdapter: PhotosAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = obtainViewModel(this)
	  initPhotosAdapter()
    getPhotos()
  }

	private fun initPhotosAdapter() {
		photosAdapter = PhotosAdapter()
		binding.rvPhotos.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = this@ListActivity.photosAdapter
		}
	}

	private fun obtainViewModel(activity: Activity): ListViewModel {
    val factory = ListViewModelFactory.createFactory(activity)
    return ViewModelProvider(this, factory).get(ListViewModel::class.java)
  }

  private fun getPhotos() {
    viewModel.getPhotos().observe(this) {
      if (it != null) {
        when (it) {
          is SuccessResponse -> {
	          binding.pbList.gone()
	          binding.tvEmptyList.gone()

	          photosAdapter.setPhotos(it.body)
          }
          is ErrorResponse -> {
	          binding.pbList.gone()
	          binding.tvEmptyList.gone()

	          showToast(it.message as String)
          }
          is EmptyResponse -> {
	          binding.pbList.gone()
	          binding.tvEmptyList.text = it.body.toString()
	          binding.tvEmptyList.visible()
          }
          is LoadingResponse -> {
	          binding.pbList.visible()
          }
        }
      }
    }
  }

	private fun showToast(msg: String) {
		Toast.makeText(
			this, msg, Toast.LENGTH_SHORT
		).show()
	}

	companion object {
		val TAG: String = ListActivity::class.java.simpleName
	}
}