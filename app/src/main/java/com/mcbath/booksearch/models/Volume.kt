package com.mcbath.booksearch.models

import android.os.Parcel
import android.os.Parcelable

data class Volume(
    val kind: String?,
    val id: String?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(VolumeInfo::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kind)
        parcel.writeString(id)
        parcel.writeString(selfLink)
        parcel.writeParcelable(volumeInfo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Volume> {
        override fun createFromParcel(parcel: Parcel): Volume {
            return Volume(parcel)
        }

        override fun newArray(size: Int): Array<Volume?> {
            return arrayOfNulls(size)
        }
    }
}