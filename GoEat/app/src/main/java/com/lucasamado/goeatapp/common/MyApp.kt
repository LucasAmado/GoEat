package com.lucasamado.goeatapp.common

import android.app.Application

class MyApp: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}