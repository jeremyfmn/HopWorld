package com.jfalck.hopworld.matchers

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class TextFieldHintMatcher(private val expectedHint: String?) :
    TypeSafeMatcher<View>() {


    companion object {
        fun withHint(hint: String?): TextFieldHintMatcher {
            return TextFieldHintMatcher(hint)
        }
    }

    override fun describeTo(description: Description?) {
        description?.apply {
            appendText("with hint: ")
            appendValue(expectedHint)
        }
    }

    override fun matchesSafely(view: View?): Boolean =
        view is TextInputLayout && view.hint == expectedHint
}
