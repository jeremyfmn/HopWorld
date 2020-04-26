package com.jfalck.hopworld.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.jfalck.hopworld.App
import com.jfalck.hopworld.R


class LoginUtils {

    interface IOnLogin {
        fun onLoginSuccess()
        fun onLoginFailed()
    }

    interface IOnSignOut {
        fun onSignOut()
    }

    companion object {
        const val FACEBOOK_TAG = "LoginUtils - Facebook"

        fun loginWithFacebook(
            button: LoginButton,
            listener: IOnLogin,
            callbackManager: CallbackManager,
            activity: AppCompatActivity
        ) {
            button.setPermissions("email", "public_profile")
            button.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(FACEBOOK_TAG, "facebook:onSuccess:$loginResult")
                    onLoginSuccess(loginResult.accessToken, activity, listener)
                }

                override fun onCancel() {
                    Log.d(FACEBOOK_TAG, "facebook:onCancel")
                    listener.onLoginFailed()
                }

                override fun onError(error: FacebookException) {
                    Log.d(FACEBOOK_TAG, "facebook:onError", error)
                    listener.onLoginFailed()
                }
            })
        }

        fun loginWithGoogle(activity: AppCompatActivity, requestCode: Int) =
            activity.startActivityForResult(
                getGoogleSignInClient(activity).signInIntent,
                requestCode
            )

        private fun onLoginSuccess(
            token: AccessToken,
            activity: AppCompatActivity,
            listener: IOnLogin
        ) {
            Log.d(FACEBOOK_TAG, "handleFacebookAccessToken:$token")

            val credential = FacebookAuthProvider.getCredential(token.token)
            App.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(FACEBOOK_TAG, "signInWithCredential:success")
                        listener.onLoginSuccess()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(FACEBOOK_TAG, "signInWithCredential:failure", task.exception)
                    }
                }
        }

        fun registerWithGoogleWithTask(
            task: Task<GoogleSignInAccount>,
            activity: AppCompatActivity,
            listener: IOnLogin
        ) {
            try {
                task.addOnCompleteListener {
                    if (task.isSuccessful) {
                        task.getResult(ApiException::class.java)?.let { account ->
                            firebaseAuthWithGoogle(account, activity, listener)
                        }
                    }
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("AuthWithGoogle", "Google sign in failed", e)
            }
        }

        private fun firebaseAuthWithGoogle(
            acct: GoogleSignInAccount,
            activity: AppCompatActivity,
            listener: IOnLogin
        ) {
            Log.d("AuthWithGoogle", "firebaseAuthWithGoogle:" + acct.id!!)

            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            App.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful && task.isComplete) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AuthWithGoogle", "signInWithCredential:success")
                        listener.onLoginSuccess()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AuthWithGoogle", "signInWithCredential:failure", task.exception)
                        listener.onLoginFailed()
                    }
                }
        }

        private fun getGoogleAccount(context: Context) =
            GoogleSignIn.getLastSignedInAccount(context)

        private fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
            val gso: GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            return GoogleSignIn.getClient(activity, gso)
        }

        fun signOut(activity: AppCompatActivity, signOutListener: IOnSignOut) {
            App.firebaseAuth.signOut()
            LoginManager.getInstance().logOut()
            if (GoogleSignIn.getLastSignedInAccount(activity) != null) {
                signOutWithGoogle(activity, signOutListener)
            } else {
                signOutListener.onSignOut()
            }
        }

        private fun signOutWithGoogle(activity: AppCompatActivity, signOutListener: IOnSignOut) {
            getGoogleSignInClient(activity).signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signOutListener.onSignOut()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("AuthWithGoogle", "signout:failure", task.exception)
                }
            }
        }

        fun trySilentSignIn(activity: AppCompatActivity, listener: IOnLogin) {
            getGoogleAccount(activity)?.let {
                registerWithGoogleWithTask(
                    getGoogleSignInClient(activity).silentSignIn(),
                    activity,
                    listener
                )
            }
            val accessToken = AccessToken.getCurrentAccessToken()
            if (accessToken != null && !accessToken.isExpired) {
                LoginManager.getInstance()
                    .logInWithReadPermissions(activity, listOf("public_profile"))
            }
        }
    }
}