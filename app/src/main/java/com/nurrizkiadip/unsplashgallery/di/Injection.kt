package com.nurrizkiadip.unsplashgallery.di

import android.app.Application
import android.content.Context
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.source.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope

object Injection {
	fun provideRepository(
		application: Application,
	): Repository {
		val remoteDataSource = RemoteDataSource.getInstance(application)

		return Repository.getInstance(remoteDataSource)
	}
}