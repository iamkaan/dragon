package com.iamkaan.dragon.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.iamkaan.dragon.Logger
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.R
import com.iamkaan.dragon.UserManager

class SignInActivity : AppCompatActivity() {

    lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        val displayer = SignInDisplayer(rootView)
        val logger = Logger()
        val userManager = UserManager(applicationContext, logger)
        val navigator = Navigator(this)

        presenter = SignInPresenter(displayer, userManager, navigator)
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting(this::startActivityForResult)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, data)
    }
}
