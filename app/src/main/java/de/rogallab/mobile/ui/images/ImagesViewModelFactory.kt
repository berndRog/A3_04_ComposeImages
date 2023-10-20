package de.rogallab.mobile.ui.images

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ImagesViewModelFactory(
   private val context: Context
) : ViewModelProvider.Factory {

   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass == ImagesViewModel::class.java) {
         return  ImagesViewModel(context) as T
      }
      throw IllegalArgumentException(
         "Unknown ViewModel class: ${modelClass.simpleName}")
   }
}