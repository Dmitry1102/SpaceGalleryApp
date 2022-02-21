package com.playsdev.testsecond.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.playsdev.testsecond.MainActivity
import com.playsdev.testsecond.MainApplication
import com.playsdev.testsecond.databinding.ActivitySplashBinding
import com.playsdev.testsecond.di.InternetModule
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val internetModule = InternetModule()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val state = internetModule.checkInternet(applicationContext)
        Log.d("AAA","$state")
        if (state) {
            interval(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
        } else {
            Snackbar.make(binding.splashActivity, "Internet is not connected", Snackbar.LENGTH_LONG)
                .show()
        }


    }


}