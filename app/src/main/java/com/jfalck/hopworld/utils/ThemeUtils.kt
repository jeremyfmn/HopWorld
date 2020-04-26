package com.jfalck.hopworld.utils

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.core.content.ContextCompat
import com.jfalck.hopworld.R

class ThemeUtils {
    companion object {
        fun setTheme(uiMode: Int, view: View, context: Context) {
            when (uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    view.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.white
                        )
                    )
                } // Night mode is not active, we're using the light theme
                Configuration.UI_MODE_NIGHT_YES -> {
                    view.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.dark_background_color
                        )
                    )
                } // Night mode is active, we're using dark theme
            }
        }
    }
}