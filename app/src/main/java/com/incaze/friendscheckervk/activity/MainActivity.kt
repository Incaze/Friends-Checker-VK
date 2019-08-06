package com.incaze.friendscheckervk.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.TextView
import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.incaze.friendscheckervk.*
import com.incaze.friendscheckervk.feed.MainFeed
import com.incaze.friendscheckervk.model.VKUser
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.auth.VKAccessToken


class MainActivity : AppCompatActivity() {

    private var adapter = MainFeed()
    private val errorTAG = "MainActivity_Error"

    // private val debugTAG = "MainActivity_DEBUG"
    private val REQUEST_CODE = 0

    private val clickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.activity_main_add_users -> {
                startAddUser(v)
            }
           /* R.id.error_user -> {
                errorUserToast()
            }*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VK.initialize(this)
        invalidateOptionsMenu()
        setListeners()
        adapter.setup(this, adapter)
    }

    /* MENU OVERRIDES*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start_parse -> {
                startParse()
                return true
            }
            R.id.login_logout -> {
                logInOut()
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
        val outStateList = adapter.returnListOfUsers() as ArrayList
        outState.putParcelableArrayList("USER_LIST", outStateList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreList = savedInstanceState.getParcelableArrayList<VKUser>("USER_LIST")
        adapter.addListOfUsers(restoreList as List<VKUser>)
        invalidateOptionsMenu()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                invalidateOptionsMenu()
            }

            override fun onLoginFailed(errorCode: Int) {
                val toast = ShowToast()
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

                    }
                }
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun createLogoutDialog(title: String, message: String): Dialog {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                VK.logout()
                adapter.removeAll()
                invalidateOptionsMenu()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        return alertDialog.create()
    }

    /* Listener */
    private fun setListeners(){
        val addUsers = findViewById<FloatingActionButton>(R.id.activity_main_add_users)
        //val errorUser = findViewById<ImageButton>(R.id.error_user)
        addUsers.setOnClickListener(clickListener)
        //errorUser.setOnClickListener(clickListener)
    }

    /* OnClick */
    fun errorUserToast(view: View){
        val toast = ShowToast()
        toast.showToast(view.context, getString(R.string.error_user))
    }

    private fun startAddUser(view: View) {
        val intent = Intent(view.context, AddUserActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun logInOut() {
        if (VK.isLoggedIn()) {
            val dialog = createLogoutDialog(
                getString(R.string.dialog_title),
                getString(R.string.dialog_message)
            )
            dialog.show()
        } else {
            VK.login(this, arrayListOf(VKScope.FRIENDS))
        }
    }

    private fun startParse(){
        if (adapter.isEmpty()) {
            val toast = ShowToast()
            toast.showToast(this, getString(R.string.user_list_is_empty))
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
            val intent = Intent(this, ParseActivity::class.java)
            intent.putExtra("USERS_LIST", dataSend)
            intent.putExtra("USERS_LIST_SIZE", dataSize)
            startActivity(intent)
        }
    }
}
