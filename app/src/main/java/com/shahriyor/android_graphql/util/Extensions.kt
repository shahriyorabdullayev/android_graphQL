package com.shahriyor.android_graphql.util

import android.app.Activity
import android.content.Intent
import android.util.Log

fun <T> Activity.launchActivity(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}

fun Activity.logger(message: String) {
    Log.d(this.javaClass.simpleName.toString(), "logger: $message")
}