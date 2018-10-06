package com.iamkaan.dragon.post

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.iamkaan.dragon.AlertDisplayer
import com.iamkaan.dragon.DataManager
import com.iamkaan.dragon.DragonDataMapper
import com.iamkaan.dragon.Logger
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.R
import com.iamkaan.dragon.StorageManager
import com.iamkaan.dragon.UserManager

class NewPostActivity : AppCompatActivity() {

    lateinit var presenter: NewPostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        val displayer = NewPostDisplayer(rootView)
        val logger = Logger()
        val userManager = UserManager(applicationContext, logger)
        val dataMapper = DragonDataMapper(userManager)
        val dataManager = DataManager(dataMapper, userManager, logger)
        val storageManager = StorageManager(logger)
        val toastDisplayer = AlertDisplayer(this)
        val navigator = Navigator(this)

        presenter = NewPostPresenter(
                applicationContext,
                displayer,
                userManager,
                dataManager,
                storageManager,
                toastDisplayer,
                navigator,
                ::startActivityForResult
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }
}
