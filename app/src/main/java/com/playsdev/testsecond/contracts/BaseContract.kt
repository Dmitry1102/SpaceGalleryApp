package com.playsdev.testsecond.contracts

class BaseContract {
    interface Presenter<in T>{
        fun unSubscribe()
        fun attachView(view : T)
    }

    interface View {

    }
}