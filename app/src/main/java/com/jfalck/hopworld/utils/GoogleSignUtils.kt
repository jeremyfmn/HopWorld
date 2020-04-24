package com.jfalck.hopworld.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jfalck.hopworld.R

class GoogleSignUtils {
    companion object {
        fun getSignInClient(activity: Activity): GoogleSignInClient {
            val gso: GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            return GoogleSignIn.getClient(activity, gso)
        }

        fun getAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    }
}