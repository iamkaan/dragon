package com.iamkaan.dragon

import android.util.Log

class Logger {

    fun e(message: String, throwable: Throwable? = null) {
        Log.e("Dragon", message, throwable)
    }
}
