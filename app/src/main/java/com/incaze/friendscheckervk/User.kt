package com.incaze.friendscheckervk

class User(name: String, surname: String, photo: Int) {

    val name: String
    val surname: String
    val photo: Int

    init{
        this.name = name
        this.surname = surname
        this.photo = photo
    }
}
