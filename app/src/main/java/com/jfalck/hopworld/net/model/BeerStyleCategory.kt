package com.jfalck.hopworld.net.model

import android.os.Parcel
import android.os.Parcelable

data class BeerStyleCategory(
    val id: Int?,
    val name: String?,
    val createDate: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

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