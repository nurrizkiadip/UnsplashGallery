package com.nurrizkiadip.unsplashgallery.data.source.remote

sealed class ApiResponse<T>(val body: T?, val message: String?)
class SuccessResponse<T>(body: T) : ApiResponse<T>(body, null)
class EmptyResponse<T>(body: T?, message: String?) : ApiResponse<T>(body, message)
class LoadingResponse<T> : ApiResponse<T>(null, null)
class ErrorResponse<T>(message: String?) : ApiResponse<T>(null, message)