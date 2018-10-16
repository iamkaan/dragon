package com.iamkaan.dragon

import com.iamkaan.dragon.home.DragonViewModel
import java.util.*

private const val TEN_MINUTES = 1000 * 60 * 10

class DataManager(
        private val dataMapper: DragonDataMapper,
        private val userManager: UserManager,
        private val logger: Logger
) {

    //TODO("Create the Firestore variable")

    fun getDragons(onComplete: (List<DragonViewModel>) -> Unit) {
        //TODO("Implement the getDragons method")
    }

    fun getFilteredDragons(onComplete: (List<DragonViewModel>) -> Unit) {
        //TODO("Implement the getFilteredDragons method")
    }

    fun postDragon(dataModel: DragonDataModel, onComplete: (success: Boolean) -> Unit) {
        //TODO("Implement the postDragon method")
    }

    private fun deleteDragon(dragonId: String, onComplete: (success: Boolean) -> Unit) {
        //TODO("Implement the deleteDragon method")
    }

    private fun updateLikes(dragonId: String, liked: Boolean, onComplete: (success: Boolean) -> Unit) {
        //TODO("Implement the updateLikes method")
    }

    private fun tenMinAgo() = Date().apply {
        time -= TEN_MINUTES
    }
}
