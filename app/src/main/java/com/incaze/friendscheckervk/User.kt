package com.incaze.friendscheckervk

import android.widget.ImageView

class User(name: String, surname: String, photo: Int) {

    val name: String
    val surname: String
    //val photo: ImageView
    val photo: Int

    init{
        this.name = name
        this.surname = surname
        this.photo = photo
    }
}
