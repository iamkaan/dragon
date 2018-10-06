package com.iamkaan.dragon.signin

import android.content.Intent
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.UserManager

private const val RC_SIGN_IN = 13

class SignInPresenter(
        private val displayer: SignInDisplayer,
        private val userManager: UserManager,
        private val navigator: Navigator
) {

    fun startPresenting(startActivityForResult: (Intent, Int) -> Unit) {
        val viewModel = SignInViewModel {
            val signInIntent = userManager.signInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        displayer.display(viewModel)
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            userManager.signInUser(data) { success ->
                if (success) {
                    navigator.toHomeScreen()
                }
            }
        }
    }
}
