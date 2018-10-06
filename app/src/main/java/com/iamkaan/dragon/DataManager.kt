package com.iamkaan.dragon

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.iamkaan.dragon.home.DragonViewModel
import java.util.*

private const val TEN_MINUTES = 1000 * 60 * 10

class DataManager(
        private val dataMapper: DragonDataMapper,
        private val userManager: UserManager,
        private val logger: Logger
) {

    private val firestore = FirebaseFirestore.getInstance()

    fun getDragons(onComplete: (List<DragonViewModel>) -> Unit) {
        firestore.collection("dragons")
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, exception ->
                    if (exception != null) {
                        logger.e("Could not get the list of dragons", exception)
                    } else {
                        val viewModels = dataMapper.mapData(querySnapshot!!, ::updateLikes, ::deleteDragon)
                        onComplete(viewModels)
                    }
                }
    }

    fun getFilteredDragons(onComplete: (List<DragonViewModel>) -> Unit) {
        firestore.collection("dragons")
                .whereGreaterThanOrEqualTo("time", tenMinAgo())
                .whereArrayContains("likes", userManager.getUserId()!!)
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, exception ->
                    if (exception != null) {
                        logger.e("Could not get the list of dragons", exception)
                    } else {
                        val viewModels = dataMapper.mapData(querySnapshot!!, ::updateLikes, ::deleteDragon)
                        onComplete(viewModels)
                    }
                }
    }

    fun postDragon(dataModel: DragonDataModel, onComplete: (success: Boolean) -> Unit) {
        firestore.collection("dragons")
                .add(dataModel)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete(true)
                    } else {
                        logger.e("Could not post the new dragon", it.exception)
                        onComplete(false)
                    }
                }
    }

    private fun deleteDragon(dragonId: String, onComplete: (success: Boolean) -> Unit) {
        firestore.collection("dragons")
                .document(dragonId)
                .delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete(true)
                    } else {
                        logger.e("Could not delete the dragon", it.exception)
                        onComplete(false)
                    }
                }
    }

    private fun updateLikes(dragonId: String, liked: Boolean, onComplete: (success: Boolean) -> Unit) {
        firestore.runTransaction { transaction ->
            val reference = firestore.collection("dragons").document(dragonId)
            val snapshot = transaction.get(reference)
            val likes = snapshot.get("likes") as ArrayList<String>
            if (liked) {
                likes.add(userManager.getUserId()!!)
            } else {
                likes.remove(userManager.getUserId()!!)
            }
            transaction.update(reference, "likes", likes)
            transaction.update(reference, "likeCount", likes.size)
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete(true)
            } else {
                logger.e("Could not update the likes for the dragon", it.exception)
                onComplete(false)
            }
        }
    }

    private fun tenMinAgo() = Date().apply {
        time -= TEN_MINUTES
    }
}
