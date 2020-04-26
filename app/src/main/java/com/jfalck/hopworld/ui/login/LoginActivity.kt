package com.jfalck.hopworld.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.jfalck.hopworld.App
import com.jfalck.hopworld.MainActivity
import com.jfalck.hopworld.R
import com.jfalck.hopworld.ui.login.signup.SignUpFragment
import com.jfalck.hopworld.utils.LoginUtils
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginUtils.IOnLogin {

    // Initialize Facebook Login button
    private val facebookCallbackManager = CallbackManager.Factory.create()

    private val loginFragment = LoginFragment.newInstance(this)
    private val signupFragment = SignUpFragment.newInstance(this)

    companion object CREATOR : Parcelable.Creator<LoginActivity> {

        const val RC_SIGN_IN = 100

        override fun createFromParcel(parcel: Parcel): LoginActivity = LoginActivity()

        override fun newArray(size: Int): Array<LoginActivity?> = arrayOfNulls(size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        initLoginButtons()
        val animslideup = AnimationUtils.loadAnimation(this, R.anim.slide_up_with_fade)
        AnimationSet(true).apply {
            interpolator = DecelerateInterpolator(1f)
            addAnimation(animslideup)
            tv_welcome.startAnimation(this)
        }

        val animrotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        AnimationSet(true).apply {
            interpolator = DecelerateInterpolator(1f)
            addAnimation(animrotate)
            iv_hop.startAnimation(this)
        }
    }

    private fun initLoginButtons() {
        LoginUtils.trySilentSignIn(this, this)
        login_button.setOnClickListener {
            loginFragment.show(supportFragmentManager, LoginFragment.TAG)
        }
        bt_google_register.setOnClickListener {
            LoginUtils.loginWithGoogle(
                this,
                RC_SIGN_IN
            )
        }
        register_button.setOnClickListener {
            signupFragment.show(supportFragmentManager, SignUpFragment.TAG)
        }
        LoginUtils.loginWithFacebook(fb_login_button, this, facebookCallbackManager, this)
    }

    private fun goToMainActivity() {
        App.setCurrentUser()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            LoginUtils.registerWithGoogleWithTask(
                GoogleSignIn.getSignedInAccountFromIntent(data), this, this
            )
        }
    }

    override fun onLoginSuccess() = goToMainActivity()

    override fun onLoginFailed() =
        Toast.makeText(this@LoginActivity, "Login attempt failed", Toast.LENGTH_LONG).show()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // do nothing
    }

    override fun describeContents(): Int = 0
}
