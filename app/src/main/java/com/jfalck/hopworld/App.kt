package com.jfalck.hopworld

import android.app.Application
import com.facebook.FacebookSdk
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.jfalck.hopworld.net.repository.BreweryRepository
import com.jfalck.hopworld.net.service.BreweryService
import com.jfalck.hopworld.utils.Constants.Companion.BREWERY_API_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {

        val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }

        val breweryService: BreweryService

        lateinit var breweryRepository: BreweryRepository

        fun setCurrentUser() =
            firebaseAuth.currentUser?.let { firebaseUser ->
                breweryRepository.firebaseUser = firebaseUser
            }

        init {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BREWERY_API_BASE_URL)
                .build()

            breweryService = retrofit.create(BreweryService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FacebookSdk.sdkInitialize(applicationContext)
        initializeAdMob()
        breweryRepository = BreweryRepository()
        breweryRepository.initDaos(baseContext)
    }

    private fun initializeAdMob() {
        MobileAds.initialize(this) {
            //TODO("Not yet implemented")
        }
        RequestConfiguration.Builder()
            .setTestDeviceIds(listOf(getString(R.string.admob_test_device_id)))
            .build().let { requestConfiguration ->
                MobileAds.setRequestConfiguration(requestConfiguration)
            }
    }
}