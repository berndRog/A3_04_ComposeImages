package de.rogallab.mobile.model

import java.util.UUID

data class Dog(
   var resourceName:  Int,    // dogs name as string resource
   val resourcePicture: Int,  // dogs picture as drawable resource
   val id: UUID = UUID.randomUUID(),
   var name: String? = null   // dog name as String
)