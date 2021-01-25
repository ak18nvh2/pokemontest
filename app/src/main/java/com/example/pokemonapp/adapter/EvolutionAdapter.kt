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

    private var mArrayListInformationPokemonBefore = ArrayList<InformationPokemon>()
    private var mArrayListInformationPokemonAfter = ArrayList<InformationPokemon>()
    private var mArrayListLv = ArrayList<Int>()
    private var mPrimaryColor = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avtAfter: ImageView = itemView.findViewById(R.id.img_afterPokemon)
        val nameAfter: TextView = itemView.findViewById(R.id.tv_afterPokemon)
        val avtBefore: ImageView = itemView.findViewById(R.id.img_pokemonBefore)
        val nameBefore: TextView = itemView.findViewById(R.id.tv_NameOfPokemonBefore)
        val level: TextView = itemView.findViewById(R.id.tv_level)
    }

    fun setList(
        listBefore: ArrayList<InformationPokemon>,
        listAfter: ArrayList<InformationPokemon>,
        listLv: ArrayList<Int>,
        primaryColor: Int
    ) {
        this.mArrayListInformationPokemonBefore = listBefore
        this.mArrayListInformationPokemonAfter = listAfter
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
        return mArrayListInformationPokemonAfter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.with(mContext)
            .load(mArrayListInformationPokemonBefore[position].sprites?.other?.officialArtwork?.frontDefault)
            .placeholder(R.drawable.egg)
            .into(holder.avtBefore)
        Picasso.with(mContext)
            .load(mArrayListInformationPokemonAfter[position].sprites?.other?.officialArtwork?.frontDefault)
            .placeholder(R.drawable.egg)
            .into(holder.avtAfter)

        holder.nameAfter.text = mArrayListInformationPokemonAfter[position].name?.capitalize()
        holder.nameBefore.text = mArrayListInformationPokemonBefore[position].name?.capitalize()
        if (mArrayListLv[position] > 0) {
            holder.level.text = "Lv.${mArrayListLv[position]}"
        }
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