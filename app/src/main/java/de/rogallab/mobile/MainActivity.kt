package de.rogallab.mobile


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import de.rogallab.mobile.ui.image.ImageViewModel
import de.rogallab.mobile.ui.image.ImageViewModelFactory
import de.rogallab.mobile.ui.image.ImageScreen
import de.rogallab.mobile.ui.theme.AppTheme

class MainActivity : BaseActivity(tag) {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      val viewModelFactory = ImageViewModelFactory(this.applicationContext)
      val viewModel = ViewModelProvider(this, viewModelFactory)
         .get(ImageViewModel::class.java)


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

//      val text = stringResource(id = R.string.dog_01)
//
//      Column(
//         horizontalAlignment = Alignment.CenterHorizontally,
//         modifier = Modifier
//            .fillMaxWidth()
//            .padding(all = 8.dp)
//      ) {
//         Image(
//            painter = painterResource(id = R.drawable.dog_01),
//            contentDescription = text,
//            contentScale = ContentScale.Crop
//         )
//         Text(
//            text = text,
//            style = MaterialTheme.typography.bodyMedium
//         )
//      }


   }
}