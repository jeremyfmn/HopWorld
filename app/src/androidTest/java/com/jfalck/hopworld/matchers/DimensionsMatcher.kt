package com.jfalck.hopworld.matchers

import android.view.View
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class DimensionsMatcher(private val expectedDimension: Int, private val code: Int) :
    TypeSafeMatcher<View>() {

    companion object {

        const val WIDTH_CODE = 0
        const val HEIGHT_CODE = 1

        /**
         * Asserting if the height of a view is the one expected
         *
         * @param height : The height of the view, in pixels
         *
         * @return a matcher for the view's height
         */
        fun withHeight(height: Int): DimensionsMatcher {
            return DimensionsMatcher(height, HEIGHT_CODE)
        }

        /**
         * Asserting if the width of a view is the one expected
         *
         * @param width : The width of the view, in pixels
         *
         * @return a matcher for the view's width
         */
        fun withWidth(width: Int): DimensionsMatcher {
            return DimensionsMatcher(width, WIDTH_CODE)
        }
    }

    override fun describeTo(description: Description?) {
        description?.apply {
            val descriptionText = if (code == WIDTH_CODE) {
                "with width: "
            } else {
                "with height: "
            }
            appendText(descriptionText)
            appendValue(expectedDimension)
        }
    }

    override fun matchesSafely(view: View?): Boolean =
        if (code == WIDTH_CODE) {
            view?.width == expectedDimension
        } else {
            view?.height == expectedDimension
        }
}