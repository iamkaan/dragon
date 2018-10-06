package com.iamkaan.dragon.post

import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iamkaan.dragon.R

class NewPostDisplayer(rootView: View) {

    private val photo: ImageView = rootView.findViewById(R.id.photo)
    private val name: EditText = rootView.findViewById(R.id.name)
    private val upload: FloatingActionButton = rootView.findViewById(R.id.upload)
    private val post: Button = rootView.findViewById(R.id.post)
    private val progress: ProgressBar = rootView.findViewById(R.id.progress)

    fun display(viewModel: NewPostViewModel) {
        photo.setImageURI(viewModel.photoUri)
        upload.setOnClickListener { viewModel.onUploadClick() }
        post.setOnClickListener {
            post.isEnabled = false
            upload.isEnabled = false
            progress.visibility = View.VISIBLE
            viewModel.onPostClick {
                post.isEnabled = true
                upload.isEnabled = true
                progress.visibility = View.GONE
            }
        }
    }

    fun getName() = name.text.toString()
}

data class NewPostViewModel(
        val photoUri: Uri,
        val onUploadClick: () -> Unit,
        val onPostClick: (onComplete: () -> Unit) -> Unit
)
