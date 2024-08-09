package de.rogallab.mobile.ui.features.images

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import de.rogallab.mobile.data.ImagesRepositoryImpl
import de.rogallab.mobile.domain.ImagesRepository
import de.rogallab.mobile.domain.ResultData
import de.rogallab.mobile.domain.entities.DogImage
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class ImagesViewModel(
   application: Application
): AndroidViewModel(application) {

   // we must fix this by using a dependency injection framework
   private val _context: Context = application.applicationContext
   private val _repository: ImagesRepository = ImagesRepositoryImpl(_context)

   // state: observable for a MutableList<DogImage>
   private var _mutableImagesUiStateFlow:MutableStateFlow<ImagesUiState>  = MutableStateFlow(ImagesUiState())
   val imagesUiStateStateFlow: StateFlow<ImagesUiState> = _mutableImagesUiStateFlow.asStateFlow()

   init {
      fetchDogs()
   }

   fun fetchDogs() {
      logDebug(TAG, "fetchDogs")
      when(val resultData = _repository.getAll()) {
         is ResultData.Success -> {
            logDebug(TAG, "Dogs fetched successfully")
            onDogsChange(resultData.data)
         }
         is ResultData.Error -> {
            val message = "Failed to fetch dogs ${resultData.throwable.localizedMessage}"
            logError(TAG, message)
         }
      }
   }

   fun onDogsChange(values: List<DogImage>) {
      // we make a copy of values so that values are not deleted if values == dogs
      val newDogs = values.toList()
      _mutableImagesUiStateFlow.update { it: ImagesUiState ->
         it.copy(dogs = values.toList())
      }
   }


   // first state whether the search is happening or not
   private val _isSearching = MutableStateFlow(false)
   val isSearching = _isSearching.asStateFlow()
   fun onToogleSearch(it: Boolean) {
      logDebug(TAG,"onToogleSearch() it $it")
      _isSearching.value = !_isSearching.value
      logDebug(TAG,"onToogleSearch() isSearching ${isSearching.value}")
      if (!_isSearching.value) {
         onSearchQueryChange("")
      }
   }

   // second state the text typed by the user
   private val _searchQuery = MutableStateFlow("")
   val searchQuery = _searchQuery.asStateFlow()
   fun onSearchQueryChange(text: String) {
      logDebug(TAG,"onSearchQueryChange() $text")
      _searchQuery.value = text
   }

   fun onFilterDogs(searchQuery: String) {
      if (searchQuery.trim().isEmpty()) {
         onToogleSearch(false)
         fetchDogs()
      } else {
         _mutableImagesUiStateFlow.value.dogs
            .filter { dog: DogImage ->
               val dogNameLower = dog.name!!.lowercase(Locale.getDefault())
               val searchLower  = searchQuery.lowercase(Locale.getDefault())
               dogNameLower.startsWith(searchLower)  // ^filter
            }
            .apply { // this is the filtered list of dogs
               onDogsChange(this)
            }
      }
   }

//   fun filter(
//   searchText = searchQuery,
//   dogs = imagesUiState.dogs,
//   onDogsFiltered = { dogs:List<DogImage> ->
//      viewModel.onDogsChange(dogs)
//      // show the first dog in list
//      if(dogs.isNotEmpty()) selectedDog = dogs[0]
//   }
//   )

   companion object {
      const val TAG = "[ImagesViewModel]"
   }

}