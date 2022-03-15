package com.playsdev.testsecond.di

import com.playsdev.testsecond.notifications.ChargedNotifications
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationModule {

    @Provides
    @Singleton
    fun notificationProvide():ChargedNotifications{
        return ChargedNotifications()
    }

}