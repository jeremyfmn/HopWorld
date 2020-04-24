package com.jfalck.hopworld.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfalck.hopworld.net.model.Beer
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BeerDao {

    /**
     * Get a beer by id.
     * @return the beer from the table with a specific id.
     */
    @Query("SELECT * FROM beers WHERE id = :id")
    fun getUserById(id: String): Flowable<Beer>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param beer the beer item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(beer: Beer): Completable

    /**
     * Delete all users.
     */
    @Query("DELETE FROM beers")
    fun deleteAllBeers()

}