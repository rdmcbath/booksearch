package com.mcbath.booksearch.models

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val kind: String?,
    val id: String?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
    val accessInfo: AccessInfo?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(VolumeInfo::class.java.classLoader),
        parcel.readParcelable(AccessInfo::class.java.classLoader),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kind)
        parcel.writeString(id)
        parcel.writeString(selfLink)
        parcel.writeParcelable(volumeInfo, flags)
        parcel.writeParcelable(accessInfo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

