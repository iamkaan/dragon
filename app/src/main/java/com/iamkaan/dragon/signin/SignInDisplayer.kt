package com.iamkaan.dragon.signin

import android.view.View
import com.google.android.gms.common.SignInButton
import com.iamkaan.dragon.R

class SignInDisplayer(rootView: View) {

    private val signInButton: SignInButton = rootView.findViewById(R.id.sign_in)

    fun display(viewModel: SignInViewModel) {
        signInButton.setOnClickListener { viewModel.onSigInClick() }
    }
}

data class SignInViewModel(val onSigInClick: () -> Unit)
