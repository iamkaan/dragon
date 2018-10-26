package com.iamkaan.dragon

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

const val token = "8884708575-ct78g3njp7g55nsjg4t783a2stq39b65.apps.googleusercontent.com"

class UserManager(context: Context, private val logger: Logger) {

    private val auth = FirebaseAuth.getInstance()
    private var signInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()

        signInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getUserId() = auth.currentUser?.uid

    fun signInUser(data: Intent?, onResult: (success: Boolean) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account, onResult)
        } catch (e: ApiException) {
            logger.e("Google sign in failed with code ${e.statusCode}", e)
            onResult(false)
        }
    }

    fun signOut(onComplete: () -> Unit) {
        auth.signOut()
        signInClient.signOut().addOnCompleteListener {
            onComplete()
        }
    }

    fun signInIntent() = signInClient.signInIntent

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, onResult: (success: Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true)
                    } else {
                        logger.e("Firebase auth failed", task.exception)
                        onResult(false)
                    }
                }
    }
}
