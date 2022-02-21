package com.playsdev.testsecond

import android.app.Application
import com.playsdev.testsecond.di.AppComponent
import com.playsdev.testsecond.di.DaggerAppComponent


class MainApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

}