package de.rogallab.mobile.domain

import de.rogallab.mobile.domain.entities.DogImage

interface ImagesRepository {
   fun getAll(): ResultData<List<DogImage>>
}