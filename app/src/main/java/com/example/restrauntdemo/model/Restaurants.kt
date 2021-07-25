package com.example.restrauntdemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Restaurants(
    val id: Long,
    val name: String? ="",
    val neighborhood: String?,
    val photograph: String?,
    val address: String?,
    val latlng: LatLng?,
    val cuisine_type: String?,
    val operating_hours: @RawValue Any?,
    val reviews: ArrayList<Review>
) : Parcelable

@Parcelize
data class Review(
    val name: String?,
    val date: String?,
    val rating: Int,
    val comments: String?,
) : Parcelable