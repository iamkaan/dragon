package com.iamkaan.dragon.post

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.iamkaan.dragon.AlertDisplayer
import com.iamkaan.dragon.DataManager
import com.iamkaan.dragon.DragonDataModel
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.R
import com.iamkaan.dragon.StorageManager
import com.iamkaan.dragon.UserManager
import java.util.*


private const val RC_IMAGE_PICKER = 17

class NewPostPresenter(
        private val context: Context,
        private val displayer: NewPostDisplayer,
        private val userManager: UserManager,
        private val dataManager: DataManager,
        private val storageManager: StorageManager,
        private val alertDisplayer: AlertDisplayer,
        private val navigator: Navigator,
        private val startActivityForResult: (Intent, Int) -> Unit
) {

    private val postInProgress = PostInProgress()

    fun startPresenting() {
        displayer.display(postInProgress.toViewModel())
    }

    private fun onUploadClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }

        startActivityForResult(intent, RC_IMAGE_PICKER)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                postInProgress.photoUri = uri
                displayer.display(postInProgress.toViewModel())
            }
        }
    }

    private fun PostInProgress.toViewModel() = NewPostViewModel(
            photoUri ?: R.drawable.dragon.toUri(),
            ::onUploadClick,
            ::onPostClick
    )

    private fun onPostClick(onComplete: () -> Unit) {
        postInProgress.toDragonDataModel { dataModel ->
            dataModel?.apply {
                dataManager.postDragon(this) { success ->
                    if (success) {
                        alertDisplayer.displayLongToast("Posted!")
                        navigator.back()
                    } else {
                        alertDisplayer.displayLongToast("Could not post the new dragon")
                    }
                    onComplete()
                }
            } ?: onComplete()
        }
    }

    private fun Int.toUri(): Uri =
            Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                            context.resources.getResourcePackageName(this) + "/" +
                            context.resources.getResourceTypeName(this) + "/" +
                            context.resources.getResourceEntryName(this)
            )

    private fun PostInProgress.toDragonDataModel(onComplete: (DragonDataModel?) -> Unit) {
        this.photoUri?.apply {
            val dragonName = displayer.getName()
            when {
                dragonName.isBlank() -> {
                    alertDisplayer.displayLongToast("Please write the name of your dragon")
                    onComplete(null)
                }
                userManager.getUserId() == null -> {
                    alertDisplayer.displayLongToast("You seem to be signed out. Try to restart the app")
                    onComplete(null)
                }
                else -> storageManager.upload(this) { uploadResult ->
                    if (!uploadResult.isSuccessful()) {
                        alertDisplayer.displayLongToast("Photo upload failed")
                        onComplete(null)
                    } else {
                        onComplete(DragonDataModel(dragonName, userManager.getUserId()!!, uploadResult.uri.toString(), Date(), emptyList()))
                    }
                }
            }
        }
                ?: alertDisplayer.displayLongToast("Please add the photo of your dragon").also { onComplete(null) }
    }
}

data class PostInProgress(var photoUri: Uri? = null, var name: String? = null)
