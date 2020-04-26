package com.jfalck.hopworld

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.jfalck.hopworld.ui.SignUpFragment
import com.jfalck.hopworld.utils.LoginUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginUtils.IOnLogin {

    companion object {
        const val RC_SIGN_IN = 100
    }

    // Initialize Facebook Login button
    private val facebookCallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initLoginButtons()
    }

    private fun initLoginButtons() {
        LoginUtils.trySilentSignIn(this, this)
        bt_google_register.setOnClickListener {
            LoginUtils.loginWithGoogle(this, RC_SIGN_IN)
        }
        register_button.setOnClickListener {
            SignUpFragment().show(supportFragmentManager, SignUpFragment.TAG)
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
        Toast.makeText(this@LoginActivity, "Login with Facebook failed", Toast.LENGTH_LONG).show()
}
