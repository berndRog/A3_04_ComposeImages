package de.rogallab.mobile.ui.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.rogallab.mobile.model.Dog
import de.rogallab.mobile.utilities.logDebug

@Composable
fun SelectLazyRow(
   dogs: List<Dog>,            // State ↓
   onDogSelect: (Dog) -> Unit  // Event ↑
) {
   val tag = "ok>SelectLazyRow      ."
   logDebug(tag, "")

   LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp)
   ) {
      items(
         items = dogs
      ) { dog ->
         ImageItem(
            dog = dog,
            onClick = {
               logDebug(tag, "Click ${it.name}")
               onDogSelect( it )
            },
            modifier = Modifier
               .size(width = 80.dp, height = 80.dp)
         )
      }
   }
}