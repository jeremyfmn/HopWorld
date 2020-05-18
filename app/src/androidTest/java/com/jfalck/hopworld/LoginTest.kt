package com.jfalck.hopworld

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jfalck.hopworld.matchers.DimensionsMatcher.Companion.withHeight
import com.jfalck.hopworld.matchers.TextFieldHintMatcher.Companion.withHint
import com.jfalck.hopworld.matchers.dpToPx
import com.jfalck.hopworld.matchers.getResourceString
import com.jfalck.hopworld.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun isLoginDialogShown() {
        onView(withId(R.id.login_button)).perform(click()).check(doesNotExist())
    }

    @Test
    fun isRegisterDialogShown() {
        onView(withId(R.id.register_button)).perform(click()).check(doesNotExist())
    }

    @Test
    fun isWelcomeTitleCorrect() {
        onView(withId(R.id.tv_welcome)).check(matches(withText(getResourceString(R.string.welcome_to_hopworld))))
    }

    @Test
    fun isGoogleButtonsHighEnough() {
        onView(withId(R.id.bt_google_register)).check(matches(withHeight(dpToPx(48))))
    }

    @Test
    fun assertLoginFields() {
        onView(withId(R.id.login_button)).perform(click())
        onView(withId(R.id.et_login_email)).check(matches(withHint(getResourceString(R.string.email))))
        onView(withId(R.id.et_login_password)).check(matches(withHint(getResourceString(R.string.password))))
    }

}