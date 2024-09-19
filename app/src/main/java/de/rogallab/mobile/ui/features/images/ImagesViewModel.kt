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
            onChangeDogs(resultData.data)
         }
         is ResultData.Error -> {
            val message = "Failed to fetch dogs ${resultData.throwable.localizedMessage}"
            logError(TAG, message)
         }
      }
   }

   fun onChangeDogs(dogs: List<DogImage>) {
      _mutableImagesUiStateFlow.update { it: ImagesUiState ->
         it.copy(
            dogs = dogs.toList()
         )
      }
   }

   // actual search query
   // event, when search query input is change by user
   private val _query = MutableStateFlow("")
   val query = _query.asStateFlow()
   fun onQueryChange(text: String) {
      logDebug(TAG,"onQueryChange() '$text'")
      _query.update { text }
   }

   // is searching active?
   // event, when the user clicks on the search icon to
   // activate/deactivate searching
   private val _isActive = MutableStateFlow(false)
   val isActive = _isActive.asStateFlow()
   // event, when the user clicks on the search icon to  activate/deactivate searching
   fun onActiveChange(it: Boolean) {
      _isActive.update { it }
      logDebug(TAG,"onActiveChange() isActive ${isActive.value}")
      if (!_isActive.value) onQueryChange("")
   }

   // event, when the user triggers the Ime.Search action (Lupe)
   fun onSearch(searchQuery: String) {
      logDebug(TAG,"onSearch() searchQuery $searchQuery")
      if (searchQuery.trim().isEmpty()) {
         // stop searching
         onActiveChange(false)
         fetchDogs()
      } else {
         // filter dogs by search query compared with dog name
         _mutableImagesUiStateFlow.value.dogs
            .filter { dog: DogImage ->
               val dogNameLower = dog.name.lowercase(Locale.getDefault())
               val searchLower  = searchQuery.trim().lowercase(Locale.getDefault())
               dogNameLower.startsWith(searchLower)  // ^filter
            }
            .apply { // this is the filtered list of dogs
               onChangeDogs(this)
            }
         onActiveChange(false)
      }
   }

   companion object {
      const val TAG = "<-ImagesViewModel"
   }

}