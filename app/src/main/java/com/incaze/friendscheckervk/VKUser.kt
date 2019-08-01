package com.incaze.friendscheckervk

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKUser(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var photo: String = "",
    var can_access_closed: Boolean = false,
    var deactivated: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean(),
        parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(photo)
        parcel.writeBoolean(can_access_closed)
        parcel.writeString(deactivated)
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
            photo = json.optString("photo_100", ""),
            can_access_closed = json.optBoolean("can_access_closed",false),
            deactivated = json.optString("deactivated", "")
        )
    }
}