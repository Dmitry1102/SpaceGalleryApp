package com.playsdev.testsecond

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.playsdev.testsecond.databinding.ActivityMainBinding
import com.playsdev.testsecond.navigator.Screens.main
import com.playsdev.testsecond.navigator.Screens.map
import com.playsdev.testsecond.notifications.ChargedNotifications
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    @Inject lateinit var router: Router
    @Inject lateinit var notification: ChargedNotifications
    @Inject lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator = object : AppNavigator(this, R.id.fragment_container) {
        override fun applyCommand(command: Command) {
            super.applyCommand(command)
            supportFragmentManager.executePendingTransactions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilterForCharging = IntentFilter()
        intentFilterForCharging.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(notification, intentFilterForCharging)

        binding.navigation.itemIconTintList = null
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> router.navigateTo(main())
                R.id.navigation_map -> router.navigateTo(map())
            }
            true
        }
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(main())))
        }
    }


    override fun onResume() {
        navigatorHolder.setNavigator(navigator)
        super.onResume()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}

