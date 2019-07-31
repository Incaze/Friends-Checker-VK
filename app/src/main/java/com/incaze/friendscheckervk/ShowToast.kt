package com.incaze.friendscheckervk

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class ShowToast {
    fun showToast(context: Context, text: String) {
        val toast = Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.show()
    }
}