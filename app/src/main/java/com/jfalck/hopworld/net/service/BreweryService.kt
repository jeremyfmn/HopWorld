package com.jfalck.hopworld.net.service

import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.BreweryDBData
import com.jfalck.hopworld.net.model.Hop
import com.jfalck.hopworld.utils.Constants.Companion.BREWERY_API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweryService {

    @GET("beer/random")
    fun getRandomBeer(
        @Query("key") key: String = BREWERY_API_KEY
    ): Observable<BreweryDBData<Beer>>

    @GET("beer/{beerId}")
    fun getBeerDetails(
        @Path("beerId") id: String,
        @Query("key") key: String = BREWERY_API_KEY
    ): Observable<BreweryDBData<BeerDetail>>

    @GET("beer/{id}/hops")
    fun getBeerHops(
        @Path("id") id: String,
        @Query("key") key: String = BREWERY_API_KEY
    ): Observable<BreweryDBData<List<Hop>>>
}
