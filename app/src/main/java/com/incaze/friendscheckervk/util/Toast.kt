package com.incaze.friendscheckervk.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class Toast {
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