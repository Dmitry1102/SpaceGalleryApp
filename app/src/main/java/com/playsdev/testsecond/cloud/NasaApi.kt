package com.playsdev.testsecond.cloud

import com.playsdev.testsecond.BuildConfig
import com.playsdev.testsecond.responce.Info
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NasaApi {

    @GET("rovers/curiosity/photos?sol=1000&api_key=${BuildConfig.API_KEY}")
    fun getNasaInfo(): Observable<Info>



}