package com.playsdev.testsecond.di

import com.playsdev.testsecond.cloud.NasaApi
import com.playsdev.testsecond.contracts.MainContract
import com.playsdev.testsecond.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun getPresenter(): MainContract.Presenter{
        return MainPresenter()
    }

}