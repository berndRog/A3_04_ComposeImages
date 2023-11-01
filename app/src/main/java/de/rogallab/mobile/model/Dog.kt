package de.rogallab.mobile.model

import java.util.UUID

data class Dog(
   var name: String? = null,  // dog name as String
   val resourcePicture: Int,  // dogs picture as drawable resource
   val id: UUID = UUID.randomUUID(),
)