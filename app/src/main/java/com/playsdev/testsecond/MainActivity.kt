package com.playsdev.testsecond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.playsdev.testsecond.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding:ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)






    }
}