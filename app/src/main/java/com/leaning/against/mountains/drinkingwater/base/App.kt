package com.leaning.against.mountains.drinkingwater.base

import android.app.Application
import com.leaning.against.mountains.drinkingwater.utils.SPUtils

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SPUtils.init(this)
    }
}