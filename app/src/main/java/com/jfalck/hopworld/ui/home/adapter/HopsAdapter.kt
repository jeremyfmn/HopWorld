package com.jfalck.hopworld.ui.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jfalck.hopworld.R
import com.jfalck.hopworld.net.model.Hop
import kotlinx.android.synthetic.main.hop_item.view.*

class HopsAdapter(private val context: Context) :
    RecyclerView.Adapter<HopsAdapter.HopsAdapterViewHolder>() {

    var itemsList: List<Hop> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopsAdapterViewHolder =
        HopsAdapterViewHolder(View.inflate(context, R.layout.hop_item, null))

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: HopsAdapterViewHolder, position: Int) {
        holder.hopName.text = itemsList[position].name
    }

    class HopsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hopName: TextView = itemView.tv_hop_name
    }
}