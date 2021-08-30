package com.mcbath.booksearch.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class AccessInfo (
    @SerializedName("webReaderLink")
    @Expose
    var webReaderLink: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(webReaderLink)
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