package de.rogallab.mobile.ui.features.images.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import de.rogallab.mobile.domain.entities.DogImage

@Composable
fun ImageItem(
   dog: DogImage,
   onClick: (DogImage) -> Unit,
   modifier: Modifier = Modifier
) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
         .clickable { onClick(dog) }
//          .border(1.dp, color = MaterialTheme.colors.secondary)
   ) {

      Image(
         painter = painterResource(dog.resourcePicture),
         contentDescription = dog.name,
         contentScale = ContentScale.Crop,
         modifier = modifier,
      )
      Text(
         text = dog.name,
         style = MaterialTheme.typography.bodyLarge
      )
   }
}