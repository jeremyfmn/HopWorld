package com.jfalck.hopworld.net.model

data class Hop(
    val id: Int?,
    val name: String?,
    val description: String?,
    val countryOfOrigin: String?,
    val alphaAcidMin: Float?,
    val betaAcidMin: Float?,
    val betaAcidMax: Float?,
    val humuleneMin: Int?,
    val humuleneMax: Int?,
    val caryophylleneMin: Float?,
    val caryophylleneMax: Float?,
    val cohumuloneMin: Int?,
    val cohumuloneMax: Int?,
    val myrceneMin: Int?,
    val myrceneMax: Int?,
    val farneseneMin: Float?,
    val farneseneMax: Float?,
    val category: String?,
    val categoryDisplay: String?,
    val createDate: String?,
    val updateDate: String?,
    val country: HopCountry?
)

data class HopCountry(
    val isoCode: String?,
    val name: String?,
    val displayName: String?,
    val isoThree: String?,
    val numberCode: Int?,
    val createDate: String?
)