package com.nurrizkiadip.unsplashgallery.ui.detail

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nurrizkiadip.unsplashgallery.R
import com.nurrizkiadip.unsplashgallery.data.source.remote.EmptyResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.ErrorResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.LoadingResponse
import com.nurrizkiadip.unsplashgallery.data.source.remote.SuccessResponse
import com.nurrizkiadip.unsplashgallery.databinding.ActivityDetailBinding
import com.nurrizkiadip.unsplashgallery.ui.list.ListActivity
import com.nurrizkiadip.unsplashgallery.ui.list.ListViewModel
import com.nurrizkiadip.unsplashgallery.ui.list.ListViewModelFactory
import com.nurrizkiadip.unsplashgallery.ui.list.PhotosAdapter
import com.nurrizkiadip.unsplashgallery.utils.gone
import com.nurrizkiadip.unsplashgallery.utils.visible

class DetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailBinding;
	private lateinit var viewModel: DetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val id = intent.getStringExtra(EXTRA_ID)

		viewModel = obtainViewModel(this)

		if (id != null) {
			getPhoto(id)
		}
	}

	private fun obtainViewModel(activity: Activity): DetailViewModel {
		val factory = ListViewModelFactory.createFactory(activity)
		return ViewModelProvider(this, factory).get(DetailViewModel::class.java)
	}

	private fun getPhoto(id: String) {
		viewModel.getPhotoById(id).observe(this) {
			if (it !== null) {
				when (it) {
					is SuccessResponse -> {
						binding.pbDetail.gone()

						Glide
							.with(this)
							.load(it.body?.regPhotoUrl)
							.centerCrop()
							.placeholder(R.drawable.ic_user)
							.into(binding.imgPhotoDetail)
						Glide
							.with(this)
							.load(it.body?.user?.profileImageUrl)
							.centerCrop()
							.placeholder(R.drawable.ic_user)
							.into(binding.userProfileImage)

						binding.username.text = it.body?.user?.username
						binding.name.text = it.body?.user?.name
						binding.totalViewsPhoto.text = it.body?.views.toString()
						binding.tvDescriptionContent.text = (it.body?.description ?: resources.getString(R.string.no_description)).toString()
					}
					is ErrorResponse -> {
						binding.pbDetail.gone()
						showToast(it.message as String)
					}
					is EmptyResponse -> {
						binding.pbDetail.gone()

						showToast(it.message as String)
					}
					is LoadingResponse -> {
						binding.pbDetail.visible()
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
		private val TAG: String = DetailActivity::class.java.simpleName
		const val PHOTO_TYPE = "photo_type"
		const val EXTRA_ID = "extra_id"
		const val EXTRA_DETAIL_TYPE = "extra_detail_type"
	}
}