package com.jfalck.hopworld.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfalck.hopworld.net.model.BeerDetail
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface BeerDetailDao {

    /**
     * Get a beer by id.
     * @return the BeerDetail from the table with a specific id.
     */
    @Query("SELECT * FROM beerDetails WHERE id = :id")
    fun getBeerDetailById(id: String): Observable<BeerDetail>

    /**
     * Get all beers by
     * @return All the BeerDetails stored in the database.
     */
    @Query("SELECT * FROM beerDetails")
    fun getBeerDetail(): Observable<List<BeerDetail>>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param beerDetail the BeerDetail item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeerDetail(beerDetail: BeerDetail): Completable

    /**
     * Delete all BeerDetails.
     */
    @Query("DELETE FROM beerDetails")
    fun deleteAllBeerDetails()

}