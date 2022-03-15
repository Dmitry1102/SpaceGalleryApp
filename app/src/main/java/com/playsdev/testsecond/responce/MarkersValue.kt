package com.playsdev.testsecond.responce

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarkersValue(
    val id: Int,
    val name:String,
    val longitude: Double,
    val width: Double,
):Parcelable
