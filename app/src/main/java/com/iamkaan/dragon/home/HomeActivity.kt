package com.iamkaan.dragon.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.iamkaan.dragon.DataManager
import com.iamkaan.dragon.DragonDataMapper
import com.iamkaan.dragon.Logger
import com.iamkaan.dragon.Navigator
import com.iamkaan.dragon.R
import com.iamkaan.dragon.UserManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        val logger = Logger()
        val userManager = UserManager(applicationContext, logger)
        val dataMapper = DragonDataMapper(userManager)
        val dataManager = DataManager(dataMapper, userManager, logger)
        val displayer = HomeDisplayer(rootView)
        val navigator = Navigator(this)

        presenter = HomePresenter(displayer, dataManager, userManager, navigator)
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                presenter.onSignOutClick()
                true
            }
            R.id.action_filter -> {
                presenter.onFilterClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
