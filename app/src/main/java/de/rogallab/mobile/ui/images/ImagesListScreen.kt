@file:OptIn(ExperimentalMaterial3Api::class)

package de.rogallab.mobile.ui.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.rogallab.mobile.R
import de.rogallab.mobile.model.Dog
import de.rogallab.mobile.utilities.logDebug
import java.util.*

// https://developer.android.com/codelabs/jetpack-compose-layouts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(
   viewModel: ImagesViewModel
) {
   val tag = "ok>ImageScreen        ."
   logDebug(tag, "Start")

   var selectedDog: Dog by remember {
      mutableStateOf(value = Dog("no name", R.drawable.dog_01))
   }

   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize()
         .padding(all = 8.dp)
   ) {

      var searchText by rememberSaveable { mutableStateOf("") }
      var searchActive by rememberSaveable { mutableStateOf(false) }

      SearchBar(
         modifier = Modifier.fillMaxWidth(),
         query = searchText,
         onQueryChange = { searchText = it },
         onSearch = { searchActive = false },
         active = searchActive,
         onActiveChange = {searchActive = it },
         placeholder = { Text("Bitte geben Sie den Namen ein") },
         leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
      ){
         logDebug(tag,"<$searchText>")
         filter(
            searchText = searchText,
            dogs = viewModel.initialDogs,
            onDogsFiltered = { dogs:List<Dog> ->
               viewModel.onDogsChange(dogs)
               // show the first dog in list
               if(dogs.isNotEmpty()) selectedDog = dogs[0]
            }
         )
      }

      Spacer(modifier = Modifier.padding(8.dp))

      SelectLazyRow(
         dogs = viewModel.dogs,
         onDogSelect = {
            selectedDog = it
            searchText = ""
            viewModel.onDogsChange(viewModel.initialDogs)
         }
      )

      Spacer(modifier = Modifier.padding(16.dp))

      ImageItem(
         dog = selectedDog,
         onClick = {},
         modifier = Modifier.size(width = 200.dp, height = 200.dp)
      )
   }
}

private fun filter(
   searchText: String,
   dogs: List<Dog>,                      // State ↓
   onDogsFiltered: (List<Dog>) -> Unit,  // Event ↑
   tag: String = "filter"
) {
   logDebug(tag, "<$searchText>")
   if (searchText.trim().isEmpty()) {
      onDogsFiltered(dogs)
   } else {
      dogs
         .filter { dog: Dog ->
            val dogNameLower = dog.name!!.lowercase(Locale.getDefault())
            val searchLower  = searchText.lowercase(Locale.getDefault())
            dogNameLower.startsWith(searchLower)  // ^filter
         }
         .apply { // this is the filtered list of dogs
            onDogsFiltered(this)
         }
   }
}