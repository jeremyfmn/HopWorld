package com.jfalck.hopworld

import android.util.Patterns
import android.view.View

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()