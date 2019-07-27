package com.incaze.friendscheckervk

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
import com.vk.api.sdk.exceptions.VKApiExecutionException
import kotlinx.android.synthetic.main.activity_main.*
import com.vk.api.sdk.requests.VKRequest
import kotlinx.android.synthetic.main.activity_dialog_add_users.*
import org.json.JSONObject


class AddUser: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_add_users)
    }

    fun cancelOnClick(view: View){
        super.onBackPressed()
    }

    fun okOnClick(view: View){
        val idString = findViewById<EditText>(R.id.id_user).text.toString()
       /* var response : JSONObject
        response ={

        }*/
       // var name = nameEditText.getText().toString()
        VK.execute(FindUserVK(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                VKUser()
            }
            override fun fail(error: VKApiExecutionException) {
            }
        })

    }
}