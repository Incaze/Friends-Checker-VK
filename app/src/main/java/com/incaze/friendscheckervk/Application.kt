package com.incaze.friendscheckervk

import android.app.Application
import com.incaze.friendscheckervk.util.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class Application: Application() {

    override fun onCreate() {
        VK.initialize(this)
        super.onCreate()
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            val toast = Toast()
            toast.showToast(applicationContext, "Токен истек, требуется повторная авторизация")
        }
    }
}