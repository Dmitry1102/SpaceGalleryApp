package com.playsdev.testsecond.presenter

import com.playsdev.testsecond.cloud.ApiService
import com.playsdev.testsecond.cloud.NasaApi
import com.playsdev.testsecond.contracts.MainContract

import com.playsdev.testsecond.responce.Info
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter : MainContract.Presenter {


    private val subscriptions = CompositeDisposable()
    private var view: MainContract.View? = null


    override fun loadNetworkAPI() {
        getList()
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    private fun getList() {
        val observable = ApiService.loadInfo()
        updateView(observable)
    }

    private fun updateView(observable: Observable<Info>) {
        val info = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                view?.onLoadList(list)
            },
                { t: Throwable? ->
                    view?.showLoadErrorMessage(t.toString())
                })
        subscriptions.add(info)
    }
}