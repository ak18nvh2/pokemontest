package com.example.pokemonapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R


class TypePokemonAdapter(var mContext: Context) :
    RecyclerView.Adapter<TypePokemonAdapter.ViewHolder>() {

    private var mListType = ArrayList<Int>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeImage: ImageView = itemView.findViewById(R.id.img_type)
    }

    fun setList(list: ArrayList<Int>) {
        this.mListType = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_type_pokemon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return this.mListType.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeImage.setImageResource(mListType[position])
    }

}