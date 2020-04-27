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
    var style: BeerStyle?,
    @ColumnInfo(name = "beerStyleId")
    val beerStyleId: Int? = style?.id
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
        parcel.readParcelable(BeerStyle::class.java.classLoader),
        parcel.readInt()
    )

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
        isRetired: String?,
        beerStyleId: Int?
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
        null,
        beerStyleId
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
        parcel.writeInt(beerStyleId ?: 0)
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
            Beer(
                "",
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