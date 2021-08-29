package com.mcbath.booksearch.models

import android.os.Parcel
import android.os.Parcelable

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(smallThumbnail)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageLinks> {
        override fun createFromParcel(parcel: Parcel): ImageLinks {
            return ImageLinks(parcel)
        }

        override fun newArray(size: Int): Array<ImageLinks?> {
            return arrayOfNulls(size)
        }
    }
}