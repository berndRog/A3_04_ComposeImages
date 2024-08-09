package de.rogallab.mobile.ui.features.images.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.entities.DogImage
import de.rogallab.mobile.ui.features.images.ImagesViewModel
import de.rogallab.mobile.domain.utilities.logDebug
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesListScreen(
   viewModel: ImagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   // observe the imagesUiStateFlow in the ViewModel
   val imagesUiState by viewModel.imagesUiStateStateFlow.collectAsStateWithLifecycle()
   // observe the searchQuery in the ViewModel
   val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
   // observe the isSearching in the ViewModel
   val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

   val initialDog = DogImage(stringResource(R.string.dog_01), R.drawable.dog_01)
   var selectedDog: DogImage? by remember { mutableStateOf(value = initialDog) }

   Column(
      modifier = Modifier.fillMaxSize()
         .padding(horizontal = 8.dp)
         .padding(top = 32.dp),

      horizontalAlignment = Alignment.CenterHorizontally,
   ) {

      DockedSearchBar(
         modifier = Modifier.fillMaxWidth(),
         // query text shown on SearchBar
         query = searchQuery,
         // event, when the search query `false`
         onQueryChange = viewModel::onSearchQueryChange,
         // event to be invoked when the user triggers the ImeAction.Search action
         onSearch = viewModel::onFilterDogs,
         // is the user currently searching
         active = isSearching,
         // event to be invoked when the user clicks on the search icon
         onActiveChange = viewModel::onToogleSearch,
         placeholder = { Text("Bitte geben Sie den Namen ein") },
         leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
         shape = SearchBarDefaults.dockedShape,
      ) {
      }

      Spacer(modifier = Modifier.padding(8.dp))

      SelectLazyRow(
         dogs = imagesUiState.dogs,
         onDogSelect = { it: DogImage ->
            selectedDog = it
            viewModel.onDogsChange(imagesUiState.dogs)
         }
      )

      Spacer(modifier = Modifier.padding(16.dp))

      selectedDog?.let { it ->
         ImageItem(
            dog = it,
            onClick = {},
            modifier = Modifier.size(width = 200.dp, height = 200.dp)
         )
      }
   }
}