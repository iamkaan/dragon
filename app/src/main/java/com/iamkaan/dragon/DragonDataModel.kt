package com.iamkaan.dragon

import java.util.*

data class DragonDataModel(val name: String, val ownerId: String, val imageUrl: String, val time: Date, val likes: List<String>) {
    constructor() : this("", "", "", Date(), emptyList())
}
