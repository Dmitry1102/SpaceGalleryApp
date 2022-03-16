package com.playsdev.testsecond.cloud

import com.playsdev.testsecond.responce.Info
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val apiService: NasaApi
    private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(NasaApi::class.java)
    }

    fun loadInfo(): Observable<Info> {
        return apiService.getNasaInfo()
    }
}