package de.rogallab.mobile.ui.images

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.R
import de.rogallab.mobile.model.Dog

class ImagesViewModel(
   private val _context: Context
): ViewModel() {

   private var _initialDogs: MutableList<Dog> = mutableListOf()
   val initialDogs: List<Dog>
      get() = _initialDogs

   // state: observable for a MutableList<Dog>
   private var _dogs:SnapshotStateList<Dog>  = mutableStateListOf()
   val dogs: SnapshotStateList<Dog>
      get() = _dogs

   init {
      val dogs = mutableListOf<Dog>()
      // ContextCompat.getString()
      dogs.add(Dog(getString(_context, R.string.dog_01), R.drawable.dog_01))
      dogs.add(Dog(getString(_context, R.string.dog_02), R.drawable.dog_02))
      dogs.add(Dog(getString(_context, R.string.dog_03), R.drawable.dog_03))
      dogs.add(Dog(getString(_context, R.string.dog_04), R.drawable.dog_04))
      dogs.add(Dog(getString(_context, R.string.dog_05), R.drawable.dog_05))
      dogs.add(Dog(getString(_context, R.string.dog_06), R.drawable.dog_06))
      dogs.add(Dog(getString(_context, R.string.dog_07), R.drawable.dog_07))
      dogs.add(Dog(getString(_context, R.string.dog_08), R.drawable.dog_08))
      // sort dogs by name
      _initialDogs = dogs.sortedBy { dog -> dog.name } as MutableList<Dog>
      onDogsChange( _initialDogs )
   }

   fun onDogsChange(values: List<Dog>) {
      // we make a copy of values so that values are not deleted if values == dogs
      val newDogs = values.toList()
      _dogs.clear()
      // observer in SnapshotStateList is fired -> Recomposition
      _dogs.addAll(newDogs)
   }
}