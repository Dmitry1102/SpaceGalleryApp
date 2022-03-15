package com.playsdev.testsecond.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.playsdev.testsecond.MainActivity
import com.playsdev.testsecond.presenter.MainPresenter
import com.playsdev.testsecond.view.MainFragment
import com.playsdev.testsecond.view.MapFragment
import com.playsdev.testsecond.view.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton


@RequiresApi(Build.VERSION_CODES.M)
@Component(modules = [ApplicationModule::class, InternetModule::class,
                      NavigationModule::class, PresenterModule::class, NotificationModule::class])
@Singleton
interface AppComponent {

    //activities
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)

    //fragment
    fun inject(fragment: MapFragment)
    fun inject(fragment: MainFragment)


}



