package com.example.restrauntdemo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menus(
    val restaurantId: Long,
    val categories: ArrayList<Categories>
) : Parcelable

@Parcelize
data class Categories(
    val id: String,
    val name: String?,

    @SerializedName("menu-items")
    val menu_items: ArrayList<MenuItems>?,
) : Parcelable

@Parcelize
data class MenuItems(
    val id: String,
    val name: String?,
    val description: String?,
    val price: String?,
    val images: ArrayList<String>,
) : Parcelable
