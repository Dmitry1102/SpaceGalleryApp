package com.playsdev.testsecond.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.playsdev.testsecond.view.DetailsFragment
import com.playsdev.testsecond.view.MainFragment
import com.playsdev.testsecond.view.MapFragment

object Screens {
    fun main() = FragmentScreen{ MainFragment() }
    fun map() = FragmentScreen{ MapFragment() }
    fun details(image: String) = FragmentScreen{ DetailsFragment(image)}



}