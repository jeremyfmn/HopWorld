package com.jfalck.hopworld.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfalck.hopworld.net.model.BeerStyle
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface BeerStyleDao {

    /**
     * Get a beerStyle by id.
     * @return the beerStyle from the table with a specific id.
     */
    @Query("SELECT * FROM beerStyle WHERE id = :id")
    fun getBeerStyleById(id: Int): Observable<BeerStyle>

    /**
     * Get all beerStyles by
     * @return All the beers stored in the database.
     */
    @Query("SELECT * FROM beerStyle")
    fun getBeerStyles(): Observable<List<BeerStyle>>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param beerStyle the beer item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeerStyle(beerStyle: BeerStyle): Completable

    /**
     * Delete all beerStyles.
     */
    @Query("DELETE FROM beerStyle")
    fun deleteAllBeerStyles()
}
