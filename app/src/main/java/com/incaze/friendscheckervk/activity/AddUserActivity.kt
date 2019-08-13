package com.incaze.friendscheckervk.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.incaze.friendscheckervk.request.execute.GetUserExecute
import com.incaze.friendscheckervk.R

class AddUserActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_add_users)
        val textUserId = findViewById<TextView>(R.id.id_user)
        textUserId.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        setListeners()
    }

    private fun setListeners(){
        val cancel = findViewById<Button>(R.id.dialog_cancel)
        val ok = findViewById<Button>(R.id.dialog_ok)
        cancel.setOnClickListener{
            super.onBackPressed()
        }
        ok.setOnClickListener{
            okOnClick()
        }
    }

    private fun okOnClick(){
        val addUserIntent = intent
        val idUser = findViewById<EditText>(R.id.id_user).text.toString()
        val request = GetUserExecute()
        val result = addUserIntent!!.getStringArrayListExtra("AddUserActivity_List")
        request.executeRequest(idUser, this, result as List<String>)
    }
}