package com.incaze.friendscheckervk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Dialog
import android.content.Intent
import android.widget.TextView
import android.app.Activity
import android.graphics.Bitmap
import com.vk.api.sdk.ui.VKConfirmationActivity.Companion.result
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import java.net.URL


class MainActivity : AppCompatActivity() {

    private val adapter = UserAdapter()
    private val errorTAG = "MainActivity_Error"
   // private val debugTAG = "MainActivity_DEBUG"
    private val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VK.initialize(this)
        if (!VK.isLoggedIn()) {
            VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {

            }
            override fun onLoginFailed(errorCode: Int) {
                Log.e(errorTAG, errorCode.toString())

            }

        }

        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val result = data!!.getParcelableExtra<VKUser>("VK_USER")
                    adapter.addUser(result)
                }
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.adapter = adapter
    }

    fun startAddUser(view: View){
        val intent = Intent(this, AddUser::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

}
