package com.nurrizkiadip.unsplashgallery.utils

import android.app.Application

open class UnsplashGalleryApp : Application() {
    open fun getBaseUrl() = "https://api.unsplash.com/"
}