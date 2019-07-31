package com.incaze.friendscheckervk


import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest

class ParseActivity : AppCompatActivity() {

    private val errorTAG = "ParseActivity_Error"
    private var parseAdapter = UserAdapter()
    private val resultArr: MutableList<VKUser> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse)
        VK.initialize(this)
        val idArray = intent.getIntegerArrayListExtra("USERS_LIST") as ArrayList<Int>
        val size = intent.getIntExtra("USERS_LIST_SIZE", 0)
        setupRecyclerView()
        dataSet(idArray, size)

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

        var failed = false
        for (i in 0 until size) {
            Log.e(errorTAG, idArray[i].toString() + " ETO ID")
            VK.execute(GetFriendlist(idArray[i]), object : VKApiCallback<List<VKUser>> {
                override fun success(result: List<VKUser>) {
                    Log.e(errorTAG, "SUCCESS")
                    //parseAdapter.addListOfUsers(result)
                    //if (result.isEmpty()) Log.e(errorTAG, "RESULT IS EMPTY")
                    //resultArr.addAll<VKUser>(result)
                    doSome(i, result)
                }

                override fun fail(error: VKApiExecutionException) {
                    Log.e(errorTAG, error.toString())
                    failed = true
                }
            })
            if (failed) {
                val toast = ShowToast()
                Log.e(errorTAG, "Ошибка при парсинге")
                toast.showToast(this, "Ошибка при парсинге")
                break
            }
        }

    }

    private fun doSome(i: Int, result: List<VKUser>){
        if (i == 0) {
            Log.e(errorTAG, "i == 0")
            resultArr.addAll<VKUser>(result)
        } else {
            resultArr.retainAll(result)
        }
    }

    private fun dataSet(idArray: ArrayList<Int>, size: Int){
        Log.e(errorTAG, "в dataSet")
        executeRequest(idArray, size)
        Log.e(errorTAG, "вышел из request")
        parseAdapter.addListOfUsers(resultArr)
        Log.e(errorTAG, "Добавил лист")

    }
}