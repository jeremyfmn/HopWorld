package com.jfalck.hopworld.matchers

import android.util.TypedValue
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry


fun getResourceString(@StringRes id: Int): String? =
    InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(id)

fun dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        InstrumentationRegistry.getInstrumentation().targetContext.resources.displayMetrics
    ).toInt()
}