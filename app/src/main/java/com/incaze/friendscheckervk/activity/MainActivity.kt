package com.incaze.friendscheckervk.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.TextView
import android.app.Activity
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.incaze.friendscheckervk.*
import com.incaze.friendscheckervk.feed.MainFeed
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.util.ActivityOnClick
import com.incaze.friendscheckervk.database.DBExecutor
import com.incaze.friendscheckervk.database.DBHelper
import com.incaze.friendscheckervk.util.Toast
import com.vk.api.sdk.auth.VKAccessToken

class MainActivity : AppCompatActivity() {

    private val adapter = MainFeed()
    private lateinit var dataHelper : DBHelper
    private val REQUEST_CODE = 0
    private val dbExecutor = DBExecutor()
    private val onClick = ActivityOnClick(this)
    private val errorTAG = "MainActivity_Error"
    private val debugTAG = "MainActivity_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(debugTAG,"onCreate")
        setContentView(R.layout.activity_main)
        dataHelper = DBHelper(this)
        if (savedInstanceState == null){
            dbExecutor.restoreDB(dataHelper, adapter, this)
            invalidateOptionsMenu()
        }
        setListeners()
    }

    override fun onDestroy() {
        dataHelper.close()
        super.onDestroy()
    }

    /* MENU OVERRIDES*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start_parse -> {
                onClick.startParse(adapter)
                return true
            }
            R.id.login_logout -> {
                onClick.logInOut(dbExecutor, dataHelper, adapter)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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
            logIcon.title = getString(R.string.logout)
            startParse.isVisible = true
            addUserButton.visibility = View.VISIBLE
        } else {
            textEmpty.text = getString(R.string.string_empty_list_auth)
            logIcon.setIcon(R.drawable.ic_unlogged)
            logIcon.title = getString(R.string.login)
            startParse.isVisible = false
            addUserButton.visibility = View.GONE
        }
        if (adapter.isEmpty()) {
            textEmpty.visibility = View.VISIBLE
        } else {
            textEmpty.visibility = View.GONE
        }
        return true
    }

    /* INSTANCE OVERRIDES*/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(debugTAG,"SaveInstance")
        val outStateList = adapter.returnListOfUsers() as ArrayList
        outState.putParcelableArrayList("USER_LIST", outStateList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(debugTAG,"RestoreInstance")
        val restoreList = savedInstanceState.getParcelableArrayList<VKUser>("USER_LIST")
        adapter.addListOfUsers(restoreList as List<VKUser>)
        adapter.setup(this, adapter, dataHelper)
        invalidateOptionsMenu()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                invalidateOptionsMenu()
            }

            override fun onLoginFailed(errorCode: Int) {
                val toast = Toast()
                toast.showToast(this@MainActivity, getString(R.string.auth_failed))
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
                        dbExecutor.insertUserIntoDB(dataHelper, result)
                    }
                }
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /* Listener */
    private fun setListeners(){
        val addUsers = findViewById<FloatingActionButton>(R.id.activity_main_add_users)
        addUsers.setOnClickListener{
            onClick.startAddUser(it, REQUEST_CODE, adapter)
        }
    }

}

