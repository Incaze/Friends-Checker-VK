package com.incaze.friendscheckervk.util

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.activity.AddUserActivity
import com.incaze.friendscheckervk.activity.ParseActivity
import com.incaze.friendscheckervk.database.DBExecutor
import com.incaze.friendscheckervk.database.DBHelper
import com.incaze.friendscheckervk.feed.MainFeed
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.request.execute.GetUserExecute
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class ActivityOnClick(someActivity: Activity) {

    private val activity = someActivity

    fun startAddUser(view: View, REQUEST_CODE: Int, adapter: MainFeed) {
        val intent = Intent(view.context, AddUserActivity::class.java)
        intent.putExtra("AddUserActivity_List", adapter.returnListOfUsersId() as ArrayList)
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

    fun logInOut(dbExecutor: DBExecutor, dataHelper: DBHelper, adapter: MainFeed) {
        if (VK.isLoggedIn()) {
            val dialog = createLogoutDialog(
                activity.getString(R.string.dialog_title),
                activity.getString(R.string.dialog_message),
                dbExecutor,
                dataHelper,
                adapter
            )
            dialog.show()
        } else {
            VK.login(activity, arrayListOf(VKScope.FRIENDS))
        }
    }

    fun startParse(adapter: MainFeed){
        if (adapter.isEmpty()) {
            val toast = Toast()
            toast.showToast(activity, activity.getString(R.string.user_list_is_empty))
        } else {
            val size = adapter.itemCount
            val data: MutableList<VKUser> = adapter.returnListOfUsers()
            val dataSend: ArrayList<Int> = arrayListOf()
            var dataSize = 0
            for (i in 0 until size) {
                if (!((!data[i].can_access_closed) or (data[i].deactivated != ""))) {
                    dataSend.add(data[i].id.toInt())
                    dataSize++
                }
            }
            val intent = Intent(activity, ParseActivity::class.java)
            intent.putExtra("USERS_LIST", dataSend)
            intent.putExtra("USERS_LIST_SIZE", dataSize)
            activity.startActivity(intent)
        }
    }

    fun backToMainActivity()
    {
        activity.finish()
    }

    fun findUserClick(){
        val addUserIntent = activity.intent
        val idUser = activity.findViewById<EditText>(R.id.id_user).text.toString()
        val request = GetUserExecute()
        val result = addUserIntent!!.getStringArrayListExtra("AddUserActivity_List")
        request.executeRequest(idUser, activity, result as List<String>)
    }

    private fun createLogoutDialog(title: String, message: String,
                                   dbExecutor: DBExecutor, dataHelper: DBHelper, adapter: MainFeed): Dialog {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                VK.logout()
                adapter.removeAll()
                dbExecutor.deleteAllUsersFromDB(dataHelper)
                activity.invalidateOptionsMenu()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        return alertDialog.create()
    }
}