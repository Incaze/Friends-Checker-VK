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


/*
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.auth.VKAccessToken
import android.content.Intent
import com.vk.api.sdk.auth.VKAuthCallback
*/

class MainActivity : AppCompatActivity() {

    private val errorTAG = "MainActivity_Error"
    lateinit var user: List<User>
    private lateinit var userAdapter: UserAdapter
    private val REQUEST_CODE = 0
   // var userList = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VK.initialize(this)
       if (!VK.isLoggedIn()) {
           VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
       }
        user = generateUserList()
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                when (requestCode) {
                    REQUEST_CODE -> {
                        if (resultCode == Activity.RESULT_OK) {
                            val idUser = data!!.getStringExtra("idUser")
                            val firstName = data!!.getStringExtra("firstName")
                            val secondName = data!!.getStringExtra("secondName")
                            val photoURL = URL(data!!.getStringExtra("photoURL"))
                            val photo: Bitmap = BitmapFactory.decodeStream(photoURL.openConnection().getInputStream())
                            //myImageView.setImageBitmap(mIcon_val)
                            //  findViewById(R.id.user_photo).setImageBitmap(photo)
                            /* val photoView = findViewById<ImageView>(R.id.user_photo)
                             userList.add(
                                 User(
                                     firstName!!,
                                     secondName!!,
                                     photoView.setImageBitmap(photo)
                                 )
                             )*/
                        }

                    }
                }
            }
            override fun onLoginFailed(errorCode: Int) {
                Log.e(errorTAG, errorCode.toString())
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_rv_users)
        userAdapter = UserAdapter(user)
        recyclerView.adapter = userAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    fun startAddUser(view: View){
        val intent = Intent(this, AddUser::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun generateUserList() : List<User>{
       val user = arrayListOf<User>()
        user.add(
            User(
                "Андрей",
                "Петров",
                R.drawable.photo_1
            )
        )
        return user
    }

}
