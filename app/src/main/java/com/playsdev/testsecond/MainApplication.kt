package com.playsdev.testsecond

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.playsdev.testsecond.di.AppComponent
import com.playsdev.testsecond.di.ApplicationModule
import com.playsdev.testsecond.di.DaggerAppComponent
import com.playsdev.testsecond.di.InternetModule


class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initializeDagger(this)
    }

    private fun initializeDagger(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
    }


}