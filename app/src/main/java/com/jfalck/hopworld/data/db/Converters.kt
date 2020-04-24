package com.jfalck.hopworld.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.jfalck.hopworld.net.model.BeerStyle
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): BeerStyle {
        val type: Type = object : TypeToken<BeerStyle?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromBeerStyle(beerStyle: BeerStyle): String {
        val gson = Gson()
        return gson.toJson(beerStyle)
    }

}