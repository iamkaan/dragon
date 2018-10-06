package com.iamkaan.dragon

import com.google.firebase.firestore.QuerySnapshot
import com.iamkaan.dragon.home.DragonViewModel
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat = SimpleDateFormat("dd MMM HH:mm", Locale.UK)

class DragonDataMapper(private val userManager: UserManager) {

    fun mapData(
            querySnapshot: QuerySnapshot,
            onDragonLike: (dragonId: String, liked: Boolean, onComplete: (Boolean) -> Unit) -> Unit,
            onDragonDelete: (dragonId: String, onComplete: (Boolean) -> Unit) -> Unit
    ): List<DragonViewModel> {
        return querySnapshot
                .map { doc ->
                    val dataModel = doc.toObject(DragonDataModel::class.java)
                    val liked = dataModel.likes.contains(userManager.getUserId())
                    DragonViewModel(
                            dataModel.name,
                            dataModel.imageUrl,
                            simpleDateFormat.format(dataModel.time),
                            userManager.getUserId()?.equals(dataModel.ownerId) ?: false,
                            liked,
                            dataModel.likes.size,
                            { onComplete ->
                                onDragonLike(doc.id, !liked) { onComplete() }
                            },
                            { onComplete ->
                                onDragonDelete(doc.id) { onComplete() }
                            }
                    )
                }
    }
}
