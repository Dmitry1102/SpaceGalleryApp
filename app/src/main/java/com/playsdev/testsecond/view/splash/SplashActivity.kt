package com.playsdev.testsecond.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.playsdev.testsecond.MainActivity
import com.playsdev.testsecond.MainApplication
import com.playsdev.testsecond.databinding.ActivitySplashBinding
import com.playsdev.testsecond.di.InternetModule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@RequiresApi(Build.VERSION_CODES.M)
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var internetModule: InternetModule
    private var state: Boolean = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawable = binding.ivSplash.drawable as AnimatedVectorDrawable
        drawable.start()

        state = internetModule.checkInternet(this)
        startSplash()
    }

    private fun startSplash() {
        if (state) {
            Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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


