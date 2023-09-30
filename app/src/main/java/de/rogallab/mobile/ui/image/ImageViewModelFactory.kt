package de.rogallab.mobile.ui.image

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ImageViewModelFactory(
   private val context: Context
) : ViewModelProvider.Factory {

   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass == ImageViewModel::class.java) {
         return  ImageViewModel(context) as T
      }
      throw IllegalArgumentException(
         "Unknown ViewModel class: ${modelClass.simpleName}")
   }
}