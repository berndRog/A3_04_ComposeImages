package de.rogallab.mobile


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import de.rogallab.mobile.ui.images.ImagesViewModel
import de.rogallab.mobile.ui.images.ImagesViewModelFactory
import de.rogallab.mobile.ui.images.ImageScreen
import de.rogallab.mobile.ui.theme.AppTheme

class MainActivity : BaseActivity(tag) {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      val viewModelFactory = ImagesViewModelFactory(this.applicationContext)
      val viewModel = ViewModelProvider(this, viewModelFactory)
         .get(ImagesViewModel::class.java)


      setContent {
         AppTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               ImageScreen(viewModel)
            }
         }
      }
   }

   companion object {
      const val isInfo = true
      const val isDebug = true
      //12345678901234567890123
      private const val tag = "ok>MainActivity       ."

   }
}


@Preview(showBackground = true)
@Composable
fun Preview() {

   AppTheme {

      val text = stringResource(id = R.string.dog_01)

      Column(
         horizontalAlignment = Alignment.CenterHorizontally,
         modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
      ) {
         Image(
            modifier = Modifier.size(250.dp,300.dp)
               .border(1.dp, color = MaterialTheme.colorScheme.tertiary),
            painter = painterResource(id = R.drawable.dog_01),
            contentDescription = text,
            contentScale = ContentScale.Crop
         )
         Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
         )
      }
   }
}