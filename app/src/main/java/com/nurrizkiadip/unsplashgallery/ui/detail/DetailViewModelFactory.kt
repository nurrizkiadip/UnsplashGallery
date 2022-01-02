package com.nurrizkiadip.unsplashgallery.ui.detail

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.di.Injection
import java.lang.reflect.InvocationTargetException

class DetailViewModelFactory(
  private val mRepository: Repository?,
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    try {
      return modelClass.getConstructor(Repository::class.java)
        .newInstance(mRepository)
    } catch (e: InstantiationException) {
      throw RuntimeException("Cannot create an instance of $modelClass", e)
    } catch (e: IllegalAccessException) {
      throw RuntimeException("Cannot create an instance of $modelClass", e)
    } catch (e: NoSuchMethodException) {
      throw RuntimeException("Cannot create an instance of $modelClass", e)
    } catch (e: InvocationTargetException) {
      throw RuntimeException("Cannot create an instance of $modelClass", e)
    }
  }

  companion object {
    @Volatile
    private var instance: DetailViewModelFactory? = null

    fun createFactory(
      activity: Activity,
    ): DetailViewModelFactory {
	    val context = activity.application

      return instance ?: synchronized(this) {
        instance ?: DetailViewModelFactory(
          Injection.provideRepository(context)
        ).apply { instance = this }
      }
    }
  }
}