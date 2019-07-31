package com.incaze.friendscheckervk


import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException

class ParseActivity : AppCompatActivity() {

    private val errorTAG = "ParseActivity_Error"
    private var parseAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse)
        VK.initialize(this)
        val idArray = intent.getIntegerArrayListExtra("USERS_LIST") as ArrayList<Int>
        val size = intent.getIntExtra("USERS_LIST_SIZE", 0)
        title = getString(R.string.parse_activity_name_finding)
        setupRecyclerView()
        executeRequest(idArray, size)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.parse_menu, menu)
        return true
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_parse_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = parseAdapter
    }

    private fun executeRequest(idArray: ArrayList<Int>, size: Int) {
        var key = 0
        for (i in 0 until size) {
            VK.execute(GetFriendlist(idArray[i]), object : VKApiCallback<List<VKUser>> {
                override fun success(result: List<VKUser>) {
                    when (key){
                        0 -> {
                            parseAdapter.addListOfUsers(result)
                            if (key == size - 1) {
                                parseAdapter.notifyListOfUsers()
                                changeTitle(parseAdapter.itemCount.toString())
                            }
                        }
                        (size - 1) -> {
                            parseAdapter.retainListOfUsers(result)
                            parseAdapter.notifyListOfUsers()
                            changeTitle(parseAdapter.itemCount.toString())
                        }
                        else -> {
                            parseAdapter.retainListOfUsers(result)
                        }
                    }
                    key++
                }
                override fun fail(error: VKApiExecutionException) {
                    Log.e(errorTAG, error.toString())

                }
            })
        }
    }

    private fun changeTitle(count: String){
        val message = getString(R.string.parse_activity_name, count)
        title = message
    }

    fun backToMainActivity(item: MenuItem)
    {
        super.onBackPressed()
    }
}