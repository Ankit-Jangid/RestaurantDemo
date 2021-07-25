package com.example.restrauntdemo.ui.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restrauntdemo.R
import com.example.restrauntdemo.model.Categories
import com.example.restrauntdemo.model.MenuItems
import com.example.restrauntdemo.model.Menus
import com.example.restrauntdemo.model.Restaurants
import kotlin.collections.ArrayList

class SearchAdapter(val context: Context, val countListener: CountListener) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>(), Filterable {

    private val TAG = this::class.java.simpleName

    private var menuList = mutableListOf<Menus>()

    //searching list
    private var categoriesList = mutableListOf<Categories>()
    private var menuItemsList = ArrayList<MenuItems>()
    private var restaurantsList = mutableListOf<Restaurants>()

    //publish list
    private var resultList = mutableListOf<ResultType>()

    //one time loading and flattening the  data is better
    // rather to search in nested list/array by iterating multiple time on query
    internal fun setMenuList(list_: ArrayList<Menus>) {
        this.menuList.clear()
        this.menuList = list_
        menuList.forEach {
            categoriesList.addAll(it.categories)
        }
        categoriesList.forEach { cat ->
            if (!cat.menu_items.isNullOrEmpty()) {
                menuItemsList.addAll(cat.menu_items)
            }
        }
    }

    internal fun setRestaurantsList(list_: ArrayList<Restaurants>) {
        this.restaurantsList.clear()
        this.restaurantsList = list_
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemName.text = resultList[position].name
        holder.itemMeta.text = resultList[position].type
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById<ImageView>(R.id.item_image)
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemMeta = view.findViewById<TextView>(R.id.item_meta)
    }

//     private fun streamFilter(query: String) {
//        val list = restaurantsList.stream().filter().collect(Collectors.toList())
//    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(str: CharSequence?): FilterResults {
                Log.d(TAG, "performFiltering str: $str")

                val query = str.toString().trim().lowercase()

                //find in the combined list
                val filteredRestaurants = restaurantsList.filter {
                    val name = it.name ?: ""
                    val cuisineType = it.cuisine_type ?: ""
                    name.lowercase().contains(query) || cuisineType.lowercase().contains(query)
                }
                val filteredMenuItemsList = menuItemsList.filter {
                    val name = it.name ?: ""
                    name.lowercase().contains(query)
                }

                resultList.clear()

                filteredRestaurants.forEach {
                    val name_ = it.name
                    if (!name_.isNullOrEmpty()) {
                        val resultType = ResultType(name_, "Restaurant")
                        resultList.add(resultType)
                    }
                }

                filteredMenuItemsList.forEach {
                    val name_ = it.name
                    if (!name_.isNullOrEmpty()) {
                        val resultType = ResultType(name_, "Dish")
                        resultList.add(resultType)
                    }
                }


                val filteredResults = FilterResults()
                filteredResults.values = resultList
                return filteredResults
            }

            override fun publishResults(str: CharSequence?, results: FilterResults?) {
                Log.d(TAG, "publishResults str: $str, results: $results")
                val list = results?.values as ArrayList<*>
                notifyDataSetChanged()
                countListener.onResultsSize(list.size)
                Log.d(TAG, "publishResults str: $str, resultList: $resultList")
            }
        }
    }

    interface CountListener {
        fun onResultsSize(count: Int)
    }
}