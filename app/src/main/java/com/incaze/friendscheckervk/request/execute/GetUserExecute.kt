package com.incaze.friendscheckervk.request.execute

import android.app.Activity
import android.util.Log
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.ShowToast
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.request.GetUserRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException

class GetUserExecute {

    private val errorTAG = "GetUserExecute_Error"

    fun executeRequest(idUser: String, activity: Activity){
        var id = idUser.removePrefix("https://vk.com/")
        id = id.removeSuffix("/")
        VK.execute(GetUserRequest(id), object: VKApiCallback<VKUser> {
            override fun success(result: VKUser) {
                val resultIntent = activity.intent
                resultIntent.putExtra("VK_USER", result)
                activity.setResult(Activity.RESULT_OK, resultIntent)
                activity.finish()
            }
            override fun fail(error: VKApiExecutionException) {
                Log.e(errorTAG, error.toString())
                val toast = ShowToast()
                toast.showToast(activity, activity.getString(R.string.user_does_not_exist))
            }
        })
    }
}