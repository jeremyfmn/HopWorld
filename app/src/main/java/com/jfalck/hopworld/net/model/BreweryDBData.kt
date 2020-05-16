package com.jfalck.hopworld.net.model

data class BreweryDBData<T>(
    val message: String = "",
    val status: String = "",
    val data: T?
)