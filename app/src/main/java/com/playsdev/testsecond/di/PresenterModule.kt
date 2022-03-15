package com.playsdev.testsecond.di

import com.playsdev.testsecond.contracts.MainContract
import com.playsdev.testsecond.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun getPresenter(): MainContract.Presenter{
        return MainPresenter()
    }

}