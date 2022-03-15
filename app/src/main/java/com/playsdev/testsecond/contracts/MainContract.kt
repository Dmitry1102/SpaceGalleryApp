package com.playsdev.testsecond.contracts

import com.playsdev.testsecond.responce.Info

interface MainContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun loadNetworkAPI()
    }

    interface View : BaseContract.View {
        fun onLoadList(list: Info)
        fun showLoadErrorMessage(error: String)
    }

}