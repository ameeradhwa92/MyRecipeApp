package com.ameeradhwa92.myrecipeapp.helper

import android.app.Activity
import android.widget.Toast

class HandleToast(internal var activity: Activity) {

    fun toast(message: String) {
        activity.runOnUiThread(object : Runnable {
            var msg = message

            override fun run() {
                Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}