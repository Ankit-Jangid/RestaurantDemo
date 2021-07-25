package com.example.restrauntdemo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restrauntdemo.R
import com.example.restrauntdemo.model.Restaurants

class RestaurantsSuggestionsAdapter(val context: Context) :
    RecyclerView.Adapter<RestaurantsSuggestionsAdapter.MyViewHolder>() {

    private var list = mutableListOf<Restaurants>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaraut_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = list[position]
        restaurant.run {
            holder.itemName.text = name
            holder.itemMeta.text = cuisine_type

            //if restaurant has image then it can be loaded as well
            val image = photograph //right now it's not URL
            Glide.with(context)
                .load(R.drawable.ic_restaurant)
                .into(holder.itemImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal fun updateListData(list_: ArrayList<Restaurants>) {
        this.list.clear()
        this.list = list_
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById<ImageView>(R.id.item_image)
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemMeta = view.findViewById<TextView>(R.id.item_meta)
    }
}