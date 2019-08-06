package com.incaze.friendscheckervk

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
        VK.addTokenExpiredHandler(tokenTracker)

    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            val toast = ShowToast()
            toast.showToast(applicationContext, "Токен истек, требуется повторная авторизация")
        }
    }
}