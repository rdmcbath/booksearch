package com.mcbath.booksearch.models

import android.os.Parcel
import android.os.Parcelable

data class VolumeInfo(
    val title: String?,
    val subtitle: String?,
    val authors: Array<String>?,
    val publishedDate: String?,
    val imageLinks: ImageLinks?,
    val previewLink: String?,
    val description: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray(),
        parcel.readString(),
        parcel.readParcelable(ImageLinks::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VolumeInfo

        if (title != other.title) return false
        if (subtitle != other.subtitle) return false
        if (!authors.contentEquals(other.authors)) return false
        if (publishedDate != other.publishedDate) return false
        if (imageLinks != other.imageLinks) return false
        if (previewLink != other.previewLink) return false
        if (description != other.previewLink) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + subtitle.hashCode()
        result = 31 * result + authors.contentHashCode()
        result = 31 * result + publishedDate.hashCode()
        result = 31 * result + imageLinks.hashCode()
        result = 31 * result + previewLink.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeStringArray(authors)
        parcel.writeString(publishedDate)
        parcel.writeParcelable(imageLinks, flags)
        parcel.writeString(previewLink)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VolumeInfo> {
        override fun createFromParcel(parcel: Parcel): VolumeInfo {
            return VolumeInfo(parcel)
        }

        override fun newArray(size: Int): Array<VolumeInfo?> {
            return arrayOfNulls(size)
        }
    }
}