package de.rogallab.mobile.ui.features.images.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.rogallab.mobile.domain.entities.DogImage
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.ui.features.images.ImagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesListScreen(
   viewModel: ImagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   val tag = "<-ImagesListScreen"

   var selectedDog: DogImage? by remember { mutableStateOf(value = null) }

   // observe the imagesUiStateFlow in the ViewModel
   val imagesUiState by viewModel.imagesUiStateStateFlow.collectAsStateWithLifecycle()

   // observe the searchQuery in the ViewModel
   val query by viewModel.query.collectAsStateWithLifecycle()
   // observe the isSearching in the ViewModel
   val isActive by viewModel.isActive.collectAsStateWithLifecycle()

   // Set selectedDog to the first item in the list if it is not already set
   if (selectedDog == null && imagesUiState.dogs.isNotEmpty()) {
      selectedDog = imagesUiState.dogs.first()
   }

   Column(
      modifier = Modifier
         .fillMaxSize()
         .padding(horizontal = 8.dp)
         .padding(top = 48.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
   ) {

      DockedSearchBar(
         modifier = Modifier.fillMaxWidth(),

         // query text shown on SearchBar
         query = query,
         onQueryChange = viewModel::onQueryChange,

         // event, when the user triggers the Ime.Search action, i.e start filtering
         onSearch = { it: String ->
            viewModel.onSearch(it)
            selectedDog = viewModel.imagesUiStateStateFlow.value.dogs.firstOrNull()
         },

         // is searching activated
         active = isActive,
         onActiveChange = viewModel::onActiveChange,

         placeholder = { Text("Bitte geben Sie den Namen ein") },
         leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
         shape = SearchBarDefaults.dockedShape,
      ){}

      SelectLazyRow(
         modifier = Modifier.padding(vertical = 16.dp),
         dogs = imagesUiState.dogs,
         onDogSelect = { it: DogImage ->
            selectedDog = it
            logDebug(tag,"dog clicked: ${it.name}")
         }
      )

      selectedDog?.let { it ->
         logDebug(tag,"selectedDog: ${selectedDog?.name}")
         ImageItem(
            dog = it,
            onClick = {},
            modifier = Modifier
               .size(width = 200.dp, height = 220.dp),
         )
      }
   }
}