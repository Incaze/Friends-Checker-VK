package com.incaze.friendscheckervk

import android.app.Activity
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import kotlin.collections.ArrayList

class GetFriendlistExecute {

    private val errorTAG = "GetFriendlistExecute"

    fun executeRequest(idArray: ArrayList<Int>, size: Int, parseAdapter : UserAdapter, activity : Activity) {
        var key = 0
        for (i in 0 until size) {
            VK.execute(GetFriendlist(idArray[i]), object : VKApiCallback<List<VKUser>> {
                override fun success(result: List<VKUser>) {
                    when (key){
                        0 -> {
                            parseAdapter.addListOfUsers(result)
                            if (key == size - 1) {
                                parseAdapter.notifyListOfUsers()
                                changeTitle(activity, parseAdapter.itemCount.toString())
                            }
                        }
                        (size - 1) -> {
                            parseAdapter.retainListOfUsers(result)
                            parseAdapter.notifyListOfUsers()
                            changeTitle(activity, parseAdapter.itemCount.toString())
                        }
                        else -> {
                            parseAdapter.retainListOfUsers(result)
                        }
                    }
                    key++
                }
                override fun fail(error: VKApiExecutionException) {
                    Log.e(errorTAG, error.toString())

                }
            })
        }
    }

    private fun changeTitle(activity: Activity, count: String){
        val message = activity.getString(R.string.parse_activity_name, count)
        activity.title = message
    }
}