package com.incaze.friendscheckervk

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import kotlinx.android.synthetic.main.activity_dialog_add_users.*
import android.util.Log
import android.view.WindowManager



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
        var idUser = findViewById<EditText>(R.id.id_user).text.toString()
        idUser = idUser.removePrefix("https://vk.com/")
        idUser = idUser.removeSuffix("/")
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
                //setResult(Activity.RESULT_FIRST_USER)
                //finish()
                val toast = ShowToast()
                toast.showToast(view.context, getString(R.string.user_does_not_exist))
            }
        })

    }
}