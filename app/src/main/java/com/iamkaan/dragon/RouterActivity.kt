package com.iamkaan.dragon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import org.joda.time.DateTime

class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logger = Logger()
        val userDataProvider = UserManager(applicationContext, logger)
        val navigator = Navigator(this)

        if (userDataProvider.getUserId() == null) {
            navigator.toSignInScreen()
        } else {
            navigator.toHomeScreen()
        }

        addMockData()
    }

    private fun addMockData() {
        val firestore = FirebaseFirestore.getInstance()

        val batch = firestore.batch()

        val reference = firestore.collection("dragons")
        batch.set(
                reference.document("falkor"),
                DragonDataModel(
                        "Falkor",
                        "BastianBalthazarBux",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2Ffalkor.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(7, 3, 31, 1).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("hungarianHorntail"),
                DragonDataModel(
                        "Hungarian Horntail",
                        "unknown",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2FhungarianHorntail.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(9, 13, 37, 11).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("sapphira"),
                DragonDataModel(
                        "Sapphira",
                        "Eragon",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2Fsapphira.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(11, 1, 1, 7).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("viserion"),
                DragonDataModel(
                        "Viserion",
                        "Night King",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2Fviserion.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(12, 23, 17, 59).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("toothless"),
                DragonDataModel(
                        "Toothless",
                        "Hiccup",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/toothless.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(13, 27, 13, 49).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("charmander"),
                DragonDataModel(
                        "Charmander",
                        "Ash Ketchum",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2Fcharmander.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(15, 37, 41, 43).minusDays(1).toDate(),
                        emptyList()
                )
        )
        batch.set(
                reference.document("drogon"),
                DragonDataModel(
                        "Drogon",
                        "Daenerys Targaryen",
                        "https://firebasestorage.googleapis.com/v0/b/stack-overflow-72ad6.appspot.com/o/MockDragons%2Fdrogon.jpg?alt=media&token=60808755-43d6-4ade-97b6-902cd72237c3",
                        DateTime().withTime(16, 43, 39, 41).minusDays(1).toDate(),
                        emptyList()
                )
        )

        batch.commit()
    }
}
