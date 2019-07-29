package com.incaze.friendscheckervk

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import com.vk.api.sdk.VKApiResponseParser

class FindUserVK(uids: String) : VKRequest<VKUser>("users.get") {
    init {
        addParam("user_ids", uids)
        addParam("fields", "photo_100")
    }

    override fun parse(r: JSONObject): VKUser {
        val users = r.getJSONArray("response")
        val result = (VKUser.parse(users.getJSONObject(0)))
        return result
    }
}