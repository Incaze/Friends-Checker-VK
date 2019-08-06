package com.incaze.friendscheckervk.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.incaze.friendscheckervk.feed.ParseFeed
import com.incaze.friendscheckervk.request.execute.GetFriendlistExecute
import com.incaze.friendscheckervk.R

class ParseActivity : AppCompatActivity() {

    private var parseAdapter = ParseFeed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.go_back -> {
                backToMainActivity()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun backToMainActivity()
    {
        super.onBackPressed()
    }

}