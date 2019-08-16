package com.incaze.friendscheckervk.request.execute

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.incaze.friendscheckervk.feed.ParseFeed
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.request.GetFriendlistRequest
import com.incaze.friendscheckervk.util.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException

import kotlin.collections.ArrayList

class GetFriendlistExecute {

    private val errorTAG = "GetFriendlistExecute"

    fun executeRequest(idArray: ArrayList<Int>, size: Int, parseAdapter : ParseFeed, activity : Activity) {
        var key = 0
        var firstExecuting = true
        val bar = activity.findViewById<ProgressBar>(R.id.progress_parse)
        bar.visibility = View.VISIBLE
        bar.max = size
        bar.progress = 0
        for (i in 0 until size) {
            VK.execute(GetFriendlistRequest(idArray[i]), object : VKApiCallback<List<VKUser>> {
                override fun success(result: List<VKUser>) {
                    if (firstExecuting) {
                        parseAdapter.addListOfUsers(result)
                        firstExecuting = false
                    }
                    when (key){
                        (size - 1) -> {
                            parseAdapter.retainListOfUsers(result)
                            finishOfExecute(parseAdapter, activity, bar)
                        }
                        else -> {
                            parseAdapter.retainListOfUsers(result)
                        }
                    }
                    key++
                    bar.incrementProgressBy(1)
                }
                override fun fail(error: VKApiExecutionException) {
                    Log.e(errorTAG, error.toString())
                    val toast = Toast()
                    toast.showToast(activity, "Произошла ошибка при загрузке данных")
                }
            })
        }
    }

    private fun finishOfExecute(parseAdapter: ParseFeed, activity: Activity, bar: ProgressBar){
        parseAdapter.notifyListOfUsers()
        changeTitle(activity, parseAdapter.itemCount.toString())
        bar.visibility = View.GONE
    }

    private fun changeTitle(activity: Activity, count: String){
        val message = activity.getString(R.string.parse_activity_name, count)
        activity.title = message
    }
}