package de.rogallab.mobile.ui.image

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.R
import de.rogallab.mobile.model.Dog

class ImageViewModel(
   private val _context: Context
): ViewModel() {

   var initialDogs: MutableList<Dog> = mutableListOf()
      private set
   // state: observable for a MutableList<Dog>
   var dogs:SnapshotStateList<Dog>  = mutableStateListOf()
      private set

   init {
      val dogs = mutableListOf<Dog>()
      dogs.add(Dog(R.string.dog_01, R.drawable.dog_01))
      dogs.add(Dog(R.string.dog_02, R.drawable.dog_02))
      dogs.add(Dog(R.string.dog_03, R.drawable.dog_03))
      dogs.add(Dog(R.string.dog_04, R.drawable.dog_04))
      dogs.add(Dog(R.string.dog_05, R.drawable.dog_05))
      dogs.add(Dog(R.string.dog_06, R.drawable.dog_06))
      dogs.add(Dog(R.string.dog_07, R.drawable.dog_07))
      dogs.add(Dog(R.string.dog_08, R.drawable.dog_08))
      // create name as String

      dogs.forEach { dog ->
         dog.name = _context.getString(dog.resourceName)
      }
      // sort dogs by name
      initialDogs = dogs.sortedBy { dog -> dog.name } as MutableList<Dog>
      onDogsChange( initialDogs )
   }

   fun onDogsChange(values: List<Dog>) {
      // we make a copy of values so that values are not deleted if values == dogs
      val newDogs = values.toList()
      dogs.clear()
      // observer in SnapshotStateList is fired -> Recomposition
      dogs.addAll(newDogs)
   }
}