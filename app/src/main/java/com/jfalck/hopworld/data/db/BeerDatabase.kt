package com.jfalck.hopworld.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.BeerStyle

/**
 * The Room database that contains the Users table
 */
@Database(entities = [Beer::class, BeerStyle::class, BeerDetail::class], version = 1)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerDao
    abstract fun beerStyleDao(): BeerStyleDao
    abstract fun beerDetailDao(): BeerDetailDao

    companion object {

        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BeerDatabase::class.java, "Beer.db"
            )
                .build()
    }
}