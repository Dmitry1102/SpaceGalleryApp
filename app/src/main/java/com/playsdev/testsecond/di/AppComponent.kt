package com.playsdev.testsecond.di

import com.playsdev.testsecond.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component( modules = [InternetModule::class])
interface AppComponent {

    fun inject(activity: SplashActivity)
}