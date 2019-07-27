package com.incaze.friendscheckervk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Dialog
import android.content.Intent
import android.widget.TextView



/*
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.auth.VKAccessToken
import android.content.Intent
import com.vk.api.sdk.auth.VKAuthCallback
*/

class MainActivity : AppCompatActivity() {

    private lateinit var user: List<User>
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = generateMovieList()
        setContentView(R.layout.activity_main)
        setupRecyclerView()

    }
    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_rv_users)
        userAdapter = UserAdapter(user)
        recyclerView.adapter = userAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    fun startAddUser(view: View){
        startActivity(Intent(this, AddUser::class.java))
    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }*/

    private fun generateMovieList() : List<User>{
       val user = arrayListOf<User>()
        user.add(
            User(
                "Андрей",
                "Петров",
                R.drawable.photo_1
            )
        )
        user.add(
            User(
                "Мишка",
                "Дураков",
                R.drawable.photo_2
            )
        )
        user.add(
            User(
                "Алексей",
                "Михалков",
                R.drawable.photo_1
            )
        )
        user.add(
            User(
                "Петр",
                "Кексов",
                R.drawable.photo_2
            )
        )
        user.add(
            User(
                "Рачок",
                "Курага",
                R.drawable.photo_1
            )
        )
        user.add(
            User(
                "Кулачок",
                "Претопов",
                R.drawable.photo_2
            )
        )
        return user
    }
}
