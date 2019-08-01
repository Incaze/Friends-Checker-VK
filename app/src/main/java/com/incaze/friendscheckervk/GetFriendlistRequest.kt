package com.incaze.friendscheckervk

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class GetFriendlistRequest(uid: Int) : VKRequest<List<VKUser>>("friends.get") {
    init {
        addParam("user_id", uid)
        addParam("fields", arrayOf("id","first_name","last_name","photo_100"))
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}