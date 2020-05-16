package com.jfalck.hopworld

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jfalck.hopworld.ui.login.LoginActivity
import com.jfalck.hopworld.utils.LoginUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LoginUtils.IOnSignOut {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_dashboard,
                    R.id.navigation_profile
                )
            )
        findNavController(R.id.nav_host_fragment).let { navController ->
            setupActionBarWithNavController(navController, appBarConfiguration)
            nav_view.setupWithNavController(navController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_action_bar_menu, menu)
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
        App.breweryRepository.clearDatabase().subscribeOn(Schedulers.io())?.subscribe {}
            ?.let { compositeDisposable.add(it) }
        LoginUtils.signOut(this, this)
    }

    private fun returnToWelcomeActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSignOut() = returnToWelcomeActivity()
}
