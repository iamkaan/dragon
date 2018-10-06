package com.iamkaan.dragon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
    }
}
