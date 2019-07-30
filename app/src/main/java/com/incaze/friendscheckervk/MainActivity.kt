package com.incaze.friendscheckervk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.TextView
import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    private var adapter = UserAdapter()
    private val errorTAG = "MainActivity_Error"
    // private val debugTAG = "MainActivity_DEBUG"
    private val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VK.initialize(this)
        setContentView(R.layout.activity_main)
        invalidateOptionsMenu()
        setupRecyclerView()
    }


    /* MENU OVERRIDES*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        val logIcon = menu.findItem(R.id.login_logout)
        val startParse = menu.findItem(R.id.start_parse)
        val addUserButton: View = findViewById(R.id.activity_main_add_users)
        val textEmpty = findViewById<TextView>(R.id.text_empty_list)
        if (VK.isLoggedIn()) {
            textEmpty.text = getString(R.string.string_empty_list_add)
            logIcon.setIcon(R.drawable.ic_logged)
            startParse.isVisible = true
            addUserButton.visibility = View.VISIBLE
        } else {
            textEmpty.text = getString(R.string.string_empty_list_auth)
            logIcon.setIcon(R.drawable.ic_unlogged)
            startParse.isVisible = false
            addUserButton.visibility = View.GONE
        }
        textEmpty.visibility = View.VISIBLE
        return true
    }

    /* INSTANCE OVERRIDES*/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.p("adapter", adapter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                invalidateOptionsMenu()
            }

            override fun onLoginFailed(errorCode: Int) {
                showToast("Необходима авторизация")
                Log.e(errorTAG, errorCode.toString())

            }
        }
        when (requestCode) {
            REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val result = data!!.getParcelableExtra<VKUser>("VK_USER")
                        val textEmpty = findViewById<TextView>(R.id.text_empty_list)
                        textEmpty.visibility = View.GONE
                        adapter.addUser(result)
                    }
                    Activity.RESULT_FIRST_USER -> {
                        showToast("Пользователя с таким ID не существует")
                    }
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
        val swipeHandler = object : SwipeToDeleteUser(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteUser(viewHolder.adapterPosition)
                if (adapter.isEmpty()) {
                    val textEmpty = findViewById<TextView>(R.id.text_empty_list)
                    textEmpty.text = getString(R.string.string_empty_list_add)
                    textEmpty.visibility = View.VISIBLE
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun startAddUser(view: View) {
        val intent = Intent(this, AddUser::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun logInOut(item : MenuItem) {
        if (VK.isLoggedIn()) {
            VK.logout()
            adapter.removeAll()
            invalidateOptionsMenu()
        } else {
            VK.login(this, arrayListOf())
        }

    }

    private fun showToast(text: String) {
        val toast = Toast.makeText(
            applicationContext,
            text,
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.show()
    }
}

