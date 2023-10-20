package de.rogallab.mobile.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.rogallab.mobile.model.Dog
import de.rogallab.mobile.utilities.logDebug

@Composable
fun ImageItem(
   dog: Dog,
   onClick: (Dog) -> Unit,
   modifier: Modifier = Modifier
) {
   val tag = "ok>ImageItem          ."
   logDebug(tag, "")

   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
         .clickable { onClick(dog) }
//          .border(1.dp, color = MaterialTheme.colors.secondary)
   ) {

      val text = stringResource(id = dog.resourceName)

      Image(
         painter = painterResource(dog.resourcePicture),
         contentDescription = text,
         contentScale = ContentScale.Crop,
         modifier = modifier,
      )
      Text(
         text = text,
         style = MaterialTheme.typography.bodyMedium
      )
   }
}