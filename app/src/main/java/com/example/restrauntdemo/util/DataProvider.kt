package com.example.restrauntdemo.util

import android.content.Context
import android.util.Log
import com.example.restrauntdemo.model.Menus
import com.example.restrauntdemo.model.Restaurants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.IOException

class DataProvider(val context: Context) {

    private val TAG = this::class.java.simpleName

    internal fun getRestaurantsList(): ArrayList<Restaurants> {
        val list = ArrayList<String>()
        val restaurantsList = ArrayList<Restaurants>()
        var jsonStr = ""
        try {
            jsonStr = context.assets?.open("restaurants.json")?.bufferedReader()
                .use { it?.readText() ?: "" }

            val restaurantsArray = JSONObject(jsonStr).optJSONArray("restaurants")
            val type = object : TypeToken<List<Restaurants>>() {}.type
            val restaurants: List<Restaurants> = Gson().fromJson(restaurantsArray?.toString(), type)
            restaurantsList.clear()
            restaurantsList.addAll(restaurants)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        Log.d(TAG, "restaurantsList: $restaurantsList")
        return restaurantsList
    }

    internal fun getMenuList(): ArrayList<Menus> {
        val menuList = ArrayList<Menus>()
        var jsonStr = ""
        try {
            jsonStr = context.assets?.open("menu.json")?.bufferedReader()
                .use { it?.readText() ?: "" }

            val menusArray = JSONObject(jsonStr).optJSONArray("menus")
            val type = object : TypeToken<List<Menus>>() {}.type
            val menus: List<Menus> = Gson().fromJson(menusArray?.toString(), type)
            menuList.clear()
            menuList.addAll(menus)


        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        Log.d(TAG, "menuList: $menuList")
        return menuList
    }


}