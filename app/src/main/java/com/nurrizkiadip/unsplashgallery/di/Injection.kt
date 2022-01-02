package com.nurrizkiadip.unsplashgallery.di

import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.data.source.remote.RemoteDataSource

object Injection {
	fun provideRepository(): Repository {
		val remoteDataSource = RemoteDataSource.getInstance()
		return Repository.getInstance(remoteDataSource)
	}
}