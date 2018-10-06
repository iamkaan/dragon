package com.iamkaan.dragon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.iamkaan.dragon.home.HomeActivity
import com.iamkaan.dragon.post.NewPostActivity
import com.iamkaan.dragon.signin.SignInActivity

class Navigator(private val activity: AppCompatActivity) {

    fun toHomeScreen() {
        toActivityClearOthers(HomeActivity::class.java)
    }

    fun toSignInScreen() {
        toActivityClearOthers(SignInActivity::class.java)
    }

    fun toNewPost() {
        toActivity(NewPostActivity::class.java)
    }

    fun back() {
        activity.finish()
    }

    private fun toActivityClearOthers(activity: Class<*>) {
        val intent = Intent(this.activity, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        this.activity.startActivity(intent)
    }

    private fun toActivity(activity: Class<*>) {
        val intent = Intent(this.activity, activity)
        this.activity.startActivity(intent)
    }
}
