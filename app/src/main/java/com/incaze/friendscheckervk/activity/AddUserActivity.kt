package com.incaze.friendscheckervk.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dialog_add_users.*
import android.view.WindowManager
import com.incaze.friendscheckervk.request.execute.GetUserExecute
import com.incaze.friendscheckervk.R


class AddUserActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_add_users)
        id_user.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun cancelOnClick(view: View){
        super.onBackPressed()
    }

    fun okOnClick(view: View){
        val idUser = findViewById<EditText>(R.id.id_user).text.toString()
        val request = GetUserExecute()
        request.executeRequest(idUser, this)
    }
}