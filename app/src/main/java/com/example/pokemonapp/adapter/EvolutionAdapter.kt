package com.example.pokemonapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.models.InformationPokemon
import com.squareup.picasso.Picasso

class EvolutionAdapter(var mContext: Context) :
    RecyclerView.Adapter<EvolutionAdapter.ViewHolder>() {

    private var mArrayListInformationPokemon = ArrayList<InformationPokemon>()
    private var mArrayListLv = ArrayList<Int>()
    private var mPrimaryColor = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avtAfter: ImageView = itemView.findViewById(R.id.img_afterPokemon)
        val nameAfter: TextView = itemView.findViewById(R.id.tv_afterPokemon)
        val avtBefore: ImageView = itemView.findViewById(R.id.img_pokemonBefore)
        val nameBefore: TextView = itemView.findViewById(R.id.tv_NameOfPokemonBefore)
        val level: TextView = itemView.findViewById(R.id.tv_level)
    }

    fun setList(list: ArrayList<InformationPokemon>, listLv: ArrayList<Int>, primaryColor: Int) {
        this.mArrayListInformationPokemon = list
        this.mPrimaryColor = primaryColor
        this.mArrayListLv = listLv
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_evolutions, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListInformationPokemon.size - 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.with(mContext)
            .load(mArrayListInformationPokemon[position].sprites?.other?.officialArtwork?.frontDefault)
            .placeholder(R.drawable.egg)
            .into(holder.avtBefore)
        Picasso.with(mContext)
            .load(mArrayListInformationPokemon[position + 1].sprites?.other?.officialArtwork?.frontDefault)
            .placeholder(R.drawable.egg)
            .into(holder.avtAfter)

        holder.nameAfter.text = mArrayListInformationPokemon[position + 1].name?.capitalize()
        holder.nameBefore.text = mArrayListInformationPokemon[position].name?.capitalize()
        holder.level.text = "Lv.${mArrayListLv[position]}"
        if (this.mPrimaryColor != -1) {
            holder.level.setTextColor(
                mContext.resources.getColor(
                    mPrimaryColor,
                    mContext.theme
                )
            )
        }
    }

}