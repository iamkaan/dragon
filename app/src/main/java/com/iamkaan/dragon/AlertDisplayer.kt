package com.iamkaan.dragon

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlertDisplayer(private val activity: AppCompatActivity) {

    fun displayLongToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}
