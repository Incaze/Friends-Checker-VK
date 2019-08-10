package com.incaze.friendscheckervk.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKUser(
    var id: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var photo: String = "",
    var can_access_closed: Boolean = false,
    var deactivated: String = "",
    var domain: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean(),
        parcel.readString()!!,
        parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(photo)
        parcel.writeBoolean(can_access_closed)
        parcel.writeString(deactivated)
        parcel.writeString(domain)
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
                = VKUser(
            id = json.optString("id", ""),
            first_name = json.optString("first_name", ""),
            last_name = json.optString("last_name", ""),
            photo = json.optString("photo_100", ""),
            can_access_closed = json.optBoolean("can_access_closed", false),
            deactivated = json.optString("deactivated", ""),
            domain = json.optString("domain", "")
        )
    }
}