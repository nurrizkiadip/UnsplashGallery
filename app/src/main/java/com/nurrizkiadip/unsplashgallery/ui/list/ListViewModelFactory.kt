package com.nurrizkiadip.unsplashgallery.ui.list

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurrizkiadip.unsplashgallery.data.Repository
import com.nurrizkiadip.unsplashgallery.di.Injection
import java.lang.reflect.InvocationTargetException

class ListViewModelFactory(
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
    private var INSTANCE: ListViewModelFactory? = null

    fun createFactory(
      activity: Activity,
    ): ListViewModelFactory {
	    val context = activity.application

      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: ListViewModelFactory(
          Injection.provideRepository(context)
        ).apply { INSTANCE = this }
      }
    }
  }
}