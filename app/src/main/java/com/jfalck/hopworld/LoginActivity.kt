package com.jfalck.hopworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jfalck.hopworld.ui.SignUpFragment
import com.jfalck.hopworld.utils.GoogleSignUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 100
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        val signInClient = GoogleSignUtils.getSignInClient(this)
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignUtils.getAccount(this)?.let {
            registerWithGoogleWithTask(signInClient.silentSignIn())
        }
        register_button.setOnClickListener {
            SignUpFragment().show(supportFragmentManager, SignUpFragment.TAG)
        }
        bt_google_register.setOnClickListener {
            startActivityForResult(signInClient.signInIntent, RC_SIGN_IN)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            registerWithGoogleWithTask(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun registerWithGoogleWithTask(task: Task<GoogleSignInAccount>) {
        try {
            task.addOnCompleteListener {
                if (task.isSuccessful) {
                    task.getResult(ApiException::class.java)?.let { account ->
                        firebaseAuthWithGoogle(account)
                    }
                }
            }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w("AuthWithGoogle", "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("AuthWithGoogle", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.isComplete) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AuthWithGoogle", "signInWithCredential:success")
                    GoogleSignUtils.getAccount(this)?.let { account ->
                        App.breweryRepository.userAccount = account
                    }
                    goToMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("AuthWithGoogle", "signInWithCredential:failure", task.exception)
                    Snackbar.make(window.decorView, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
