package com.jfalck.hopworld.net.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "beerStyle")
data class BeerStyle(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "shortName")
    val shortName: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "ibuMin")
    val ibuMin: String?,
    @ColumnInfo(name = "ibuMax")
    val ibuMax: String?,
    @ColumnInfo(name = "abvMin")
    val abvMin: String?,
    @ColumnInfo(name = "abvMax")
    val abvMax: String?,
    @ColumnInfo(name = "srmMin")
    val srmMin: String?,
    @ColumnInfo(name = "srmMax")
    val srmMax: String?,
    @ColumnInfo(name = "ogMin")
    val ogMin: String?,
    @ColumnInfo(name = "fgMin")
    val fgMin: String?,
    @ColumnInfo(name = "fgMax")
    val fgMax: String?,
    @ColumnInfo(name = "createDate")
    val createDate: String?,
    @ColumnInfo(name = "updateDate")
    val updateDate: String?,
    @Ignore
    val category: BeerStyleCategory?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(BeerStyleCategory::class.java.classLoader)
    )

    //Constructor used by Room
    constructor(
        id: Int,
        categoryId: Int?,
        name: String?,
        shortName: String?,
        description: String?,
        ibuMin: String?,
        ibuMax: String?,
        abvMin: String?,
        abvMax: String?,
        srmMin: String?,
        srmMax: String?,
        ogMin: String?,
        fgMin: String?,
        fgMax: String?,
        createDate: String?,
        updateDate: String?
    ) : this(
        id,
        categoryId,
        name,
        shortName,
        description,
        ibuMin,
        ibuMax,
        abvMin,
        abvMax,
        srmMin,
        srmMax,
        ogMin,
        fgMin,
        fgMax,
        createDate,
        updateDate,
        null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(categoryId)
        parcel.writeString(name)
        parcel.writeString(shortName)
        parcel.writeString(description)
        parcel.writeString(ibuMin)
        parcel.writeString(ibuMax)
        parcel.writeString(abvMin)
        parcel.writeString(abvMax)
        parcel.writeString(srmMin)
        parcel.writeString(srmMax)
        parcel.writeString(ogMin)
        parcel.writeString(fgMin)
        parcel.writeString(fgMax)
        parcel.writeString(createDate)
        parcel.writeString(updateDate)
        parcel.writeParcelable(category, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeerStyle> {

        val DEFAULT = BeerStyle(
            0,
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

        override fun createFromParcel(parcel: Parcel): BeerStyle {
            return BeerStyle(parcel)
        }

        override fun newArray(size: Int): Array<BeerStyle?> {
            return arrayOfNulls(size)
        }
    }
}