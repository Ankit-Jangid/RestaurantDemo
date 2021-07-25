package com.example.restrauntdemo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restrauntdemo.R
import com.example.restrauntdemo.model.Categories
import com.example.restrauntdemo.model.MenuItems
import com.example.restrauntdemo.model.Menus
import com.example.restrauntdemo.model.Restaurants

class SuggestionsAdapter(val context: Context) :
    RecyclerView.Adapter<SuggestionsAdapter.MyViewHolder>() {

    private var list = mutableListOf<Menus>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggestions, parent, false)
        return MyViewHolder(view)
    }

    //can be extended for other feature, so it's made separate
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        list[position].let { menuItem ->
            val categoryName = menuItem.categories[0].name ?: ""
            holder.itemName.text = categoryName
            holder.itemMeta.text = ""
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal fun updateListData(list_: ArrayList<Menus>) {
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