package com.incaze.friendscheckervk

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKUser(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val photo: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKUser> {
        override fun createFromParcel(parcel: Parcel): VKUser {
            return VKUser(parcel)
        }

        override fun newArray(size: Int): Array<VKUser?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject)
                = VKUser(id = json.optString("id", ""),
            firstName = json.optString("first_name", ""),
            lastName = json.optString("last_name", ""),
            photo = json.optString("photo_50", ""))
    }
}