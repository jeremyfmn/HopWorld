package com.jfalck.hopworld.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jfalck.hopworld.R
import com.jfalck.hopworld.net.model.Beer
import kotlinx.android.synthetic.main.beer_item.view.*

class BeerSuggestionAdapter(
    private val context: Context,
    private val listener: IOnItemClick
) :
    RecyclerView.Adapter<BeerSuggestionAdapter.BeerSuggestionAdapterViewHolder>() {

    interface IOnItemClick {
        fun onItemClicked(beer: Beer)
    }

    var itemsList: MutableList<Beer> = mutableListOf()

    fun addBeer(beer: Beer) {
        if (!itemsList.contains(beer)) {
            itemsList.add(beer)
            notifyItemInserted(itemsList.indexOf(beer))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerSuggestionAdapterViewHolder =
        BeerSuggestionAdapterViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.beer_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: BeerSuggestionAdapterViewHolder, position: Int) {
        itemsList[position].apply {
            holder.name.text = name ?: "Nom inconnu"
            holder.abv.text = getString(abv, true)
            holder.ibu.text = getString(ibu)
            holder.style.text = style?.shortName
            holder.container.setOnClickListener {
                listener.onItemClicked(this)
            }
        }
    }

    private fun getString(value: String?, isAbv: Boolean = false): String {
        return if (value == null) {
            context.getString(if (isAbv) R.string.unknown_abv else R.string.unknown_ibu)
        } else {
            context.getString(if (isAbv) R.string.abv else R.string.ibu, value)
        }
    }

    class BeerSuggestionAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tv_beer_name
        val abv: TextView = itemView.tv_abv
        val ibu: TextView = itemView.tv_ibu
        val style: TextView = itemView.tv_beer_style
        val container: View = itemView.beer_container
    }
}