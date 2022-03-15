package com.playsdev.testsecond.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NavigationModule @Inject constructor() {

    @Provides
    @Singleton
    fun getCicerone():Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun getRouter(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    @Singleton
    fun getNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()
}