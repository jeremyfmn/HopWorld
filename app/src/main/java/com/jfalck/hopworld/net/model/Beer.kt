package com.jfalck.hopworld.net.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class Beer(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String = "0",
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "nameDisplay")
    val nameDisplay: String?,
    @ColumnInfo(name = "abv")
    val abv: String?,
    @ColumnInfo(name = "ibu")
    val ibu: String?,
    @ColumnInfo(name = "styleId")
    val styleId: String?,
    @ColumnInfo(name = "status")
    val status: String?,
    @ColumnInfo(name = "statusDisplay")
    val statusDisplay: String?,
    @ColumnInfo(name = "createDate")
    val createDate: String?,
    @ColumnInfo(name = "updateDate")
    val updateDate: String?,
    @ColumnInfo(name = "isRetired")
    val isRetired: String?,
    @Ignore
    val style: BeerStyle?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "0",
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
        parcel.readParcelable(BeerStyle::class.java.classLoader)
    ) {
    }

    //Constructor used by Room
    constructor(
        id: String,
        name: String?,
        nameDisplay: String?,
        abv: String?,
        ibu: String?,
        styleId: String?,
        status: String?,
        statusDisplay: String?,
        createDate: String?,
        updateDate: String?,
        isRetired: String?
    ) : this(
        id,
        name,
        nameDisplay,
        abv,
        ibu,
        styleId,
        status,
        statusDisplay,
        createDate,
        updateDate,
        isRetired,
        null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(nameDisplay)
        parcel.writeString(abv)
        parcel.writeString(ibu)
        parcel.writeString(styleId)
        parcel.writeString(status)
        parcel.writeString(statusDisplay)
        parcel.writeString(createDate)
        parcel.writeString(updateDate)
        parcel.writeString(isRetired)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Beer> {
        override fun createFromParcel(parcel: Parcel): Beer {
            return Beer(parcel)
        }

        override fun newArray(size: Int): Array<Beer?> {
            return arrayOfNulls(size)
        }

        val DEFAULT =
            Beer("", null, null, null, null, null, null, null, null, null, null, null)
    }
}

data class BeerStyle(
    val id: Int?,
    val categoryId: Int?,
    val name: String?,
    val shortName: String?,
    val description: String?,
    val ibuMin: String?,
    val ibuMax: String?,
    val abvMin: String?,
    val abvMax: String?,
    val srmMin: String?,
    val srmMax: String?,
    val ogMin: String?,
    val fgMin: String?,
    val fgMax: String?,
    val createDate: String?,
    val updateDate: String?,
    val category: BeerStyleCategory?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
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
        override fun createFromParcel(parcel: Parcel): BeerStyle {
            return BeerStyle(parcel)
        }

        override fun newArray(size: Int): Array<BeerStyle?> {
            return arrayOfNulls(size)
        }
    }
}

data class BeerStyleCategory(
    val id: Int?,
    val name: String?,
    val createDate: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(createDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeerStyleCategory> {
        override fun createFromParcel(parcel: Parcel): BeerStyleCategory {
            return BeerStyleCategory(parcel)
        }

        override fun newArray(size: Int): Array<BeerStyleCategory?> {
            return arrayOfNulls(size)
        }
    }
}