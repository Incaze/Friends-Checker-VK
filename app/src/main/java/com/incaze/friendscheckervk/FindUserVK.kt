package com.incaze.friendscheckervk

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import com.vk.api.sdk.VKApiResponseParser

class FindUserVK(uids: IntArray = intArrayOf()) : VKRequest<List<VKUser>>("users.get") {
    init {
        if (uids.isNotEmpty()) {
            addParam("user_ids", uids.joinToString(","))
        }
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONArray("response")
        val result = ArrayList<VKUser>()
            result.add(VKUser.parse(users.getJSONObject(0)))
        return result
    }
}