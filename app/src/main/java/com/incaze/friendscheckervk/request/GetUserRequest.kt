package com.incaze.friendscheckervk.request

import com.incaze.friendscheckervk.model.VKUser
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class GetUserRequest(uids: String) : VKRequest<VKUser>("users.get") {
    init {
        addParam("user_ids", uids)
        addParam("fields", arrayOf("photo_100","can_access_closed","deactivated"))
    }

    override fun parse(r: JSONObject): VKUser {
        val users = r.getJSONArray("response")
        val result = (VKUser.parse(users.getJSONObject(0)))
        return result
    }
}