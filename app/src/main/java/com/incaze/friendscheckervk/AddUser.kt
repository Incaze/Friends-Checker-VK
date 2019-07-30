package com.incaze.friendscheckervk

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKApiExecutionException
import kotlinx.android.synthetic.main.activity_main.*
import com.vk.api.sdk.requests.VKRequest
import kotlinx.android.synthetic.main.activity_dialog_add_users.*
import org.json.JSONObject
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT




class AddUser: AppCompatActivity(){

    private val errorTAG = "AddUser_Error"
    private val debugTAG = "AddUser_Debug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_add_users)
        id_user.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }



    fun cancelOnClick(view: View){
        super.onBackPressed()
    }

    fun okOnClick(view: View){
        val idUser = findViewById<EditText>(R.id.id_user).text.toString()
        VK.execute(FindUserVK(idUser), object: VKApiCallback<VKUser> {
            override fun success(result: VKUser) {
                Log.e(debugTAG, "Entered to success")
                val resultIntent = intent
                resultIntent.putExtra("VK_USER", result)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            override fun fail(error: VKApiExecutionException) {
                Log.e(errorTAG, error.toString())
                setResult(Activity.RESULT_FIRST_USER)
                finish()
            }
        })

    }
}