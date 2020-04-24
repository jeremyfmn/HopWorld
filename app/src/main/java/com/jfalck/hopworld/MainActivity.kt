package com.jfalck.hopworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.jfalck.hopworld.net.service.BreweryService
import com.jfalck.hopworld.utils.Constants.Companion.BREWERY_API_BASE_URL
import com.jfalck.hopworld.utils.GoogleSignUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    val service: BreweryService by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BREWERY_API_BASE_URL)
            .build()

        retrofit.create(BreweryService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                doLogout()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doLogout() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            signOutWithGoogle()
        } else {
            returnToWelcomeActivity()
        }
    }

    private fun signOutWithGoogle() {
        GoogleSignUtils.getSignInClient(this).signOut().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                returnToWelcomeActivity()
            } else {
                // If sign in fails, display a message to the user.
                Log.w("AuthWithGoogle", "signout:failure", task.exception)
                Snackbar.make(
                    window.decorView,
                    "SignOut Failed.",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun returnToWelcomeActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
