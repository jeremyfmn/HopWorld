package com.jfalck.hopworld.net.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "beerDetails")
data class BeerDetail(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String = "0",
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "nameDisplay")
    val nameDisplay: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "abv")
    val abv: String?,
    @ColumnInfo(name = "glasswareId")
    val glasswareId: String?,
    @ColumnInfo(name = "srmId")
    val srmId: Int?,
    @ColumnInfo(name = "availableId")
    val availableId: Int?,
    @ColumnInfo(name = "styleId")
    val styleId: Int?,
    @ColumnInfo(name = "isOrganic")
    val isOrganic: String?,
    @ColumnInfo(name = "isRetired")
    val isRetired: String?,
    @Ignore
    val labels: Labels?,
    @ColumnInfo(name = "status")
    val status: String?,
    @ColumnInfo(name = "statusDisplay")
    val statusDisplay: String?,
    @ColumnInfo(name = "createDate")
    val createDate: String?,
    @ColumnInfo(name = "updateDate")
    val updateDate: String?,
    @Ignore
    val glass: Glass?,
    @Ignore
    val srm: Srm?,
    @Ignore
    val available: BeerAvailability?
) {

    // Constructor used by room
    constructor(
        id: String,
        name: String,
        nameDisplay: String,
        description: String?,
        abv: String,
        glasswareId: String,
        srmId: Int,
        availableId: Int,
        styleId: Int,
        isOrganic: String,
        isRetired: String,
        status: String,
        statusDisplay: String,
        createDate: String,
        updateDate: String
    ) : this(
        id,
        name,
        nameDisplay,
        description,
        abv,
        glasswareId,
        srmId,
        availableId,
        styleId,
        isOrganic,
        isRetired,
        null,
        status,
        statusDisplay,
        createDate,
        updateDate,
        null,
        null,
        null
    )

    companion object {
        val DEFAULT = BeerDetail(
            "0",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}

data class Labels(
    val icon: String?,
    val medium: String?,
    val large: String?,
    val contentAwareIcon: String?,
    val contentAwareMedium: String?,
    val contentAwareLarge: String?
)

data class Glass(
    val id: Int?,
    val name: String?,
    val createDate: String?
)

data class Srm(
    val id: Int?,
    val name: String?,
    val hex: String?
)

data class BeerAvailability(
    val id: Int?,
    val name: String?,
    val description: String?
)