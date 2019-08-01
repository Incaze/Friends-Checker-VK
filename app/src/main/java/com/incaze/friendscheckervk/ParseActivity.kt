package com.incaze.friendscheckervk

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.api.sdk.VK

class ParseActivity : AppCompatActivity() {

    private var parseAdapter = AdapterParseActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse)
        VK.initialize(this)
        val idArray = intent.getIntegerArrayListExtra("USERS_LIST") as ArrayList<Int>
        val size = intent.getIntExtra("USERS_LIST_SIZE", 0)
        title = getString(R.string.parse_activity_name_finding)
        parseAdapter.setup(this, parseAdapter)
        val request = GetFriendlistExecute()
        request.executeRequest(idArray, size, parseAdapter, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.parse_menu, menu)
        return true
    }


    fun backToMainActivity(item: MenuItem)
    {
        super.onBackPressed()
    }
}