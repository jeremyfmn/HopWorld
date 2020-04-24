package com.jfalck.hopworld

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jfalck.hopworld.net.repository.BreweryRepository
import com.jfalck.hopworld.net.service.BreweryService
import com.jfalck.hopworld.utils.Constants.Companion.BREWERY_API_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        val breweryService: BreweryService

        lateinit var breweryRepository: BreweryRepository

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
        breweryRepository = BreweryRepository()
        breweryRepository.initDaos(baseContext)
    }
}