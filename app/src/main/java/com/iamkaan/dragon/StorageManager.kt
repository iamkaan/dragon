package com.iamkaan.dragon

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class StorageManager(private val logger: Logger) {

    val storage = FirebaseStorage.getInstance()

    fun upload(photoUri: Uri, onComplete: (result: FileUploadResult) -> Unit) {
        val photoRef = storage.reference.child("images/${photoUri.lastPathSegment}")
        val uploadTask = photoRef.putFile(photoUri)

        uploadTask.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            if (!task.isSuccessful) {
                logger.e("Photo upload failed", task.exception)
                onComplete(FileUploadResult())
            }
            return@continueWithTask photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(FileUploadResult(task.result))
            } else {
                logger.e("Could not get the photo download URL", task.exception)
                onComplete(FileUploadResult())
            }
        }
    }
}

data class FileUploadResult(val uri: Uri? = null) {
    fun isSuccessful() = uri != null
}
