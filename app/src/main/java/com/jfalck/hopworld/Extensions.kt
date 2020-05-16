package com.jfalck.hopworld

import android.util.Patterns
import android.view.View
import com.jfalck.hopworld.utils.Constants.Companion.SIMPLE_FADE_DURATION

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

fun View.fadeIn() {
    if (visibility == View.GONE) {
        alpha = 0f
        makeVisible()
        animate().alpha(1f).setDuration(SIMPLE_FADE_DURATION).setListener(null)
    }
}