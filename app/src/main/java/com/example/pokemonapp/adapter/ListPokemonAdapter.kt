package com.example.pokemonapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.Utility
import com.example.pokemonapp.models.InformationPokemon
import com.squareup.picasso.Picasso

class ListPokemonAdapter(
    var mContext: Context,
    var iListPokemonWithActivity: IListPokemonWithActivity
) : RecyclerView.Adapter<ListPokemonAdapter.ViewHolder>() {
    private var mListPokemon = ArrayList<InformationPokemon>()
    private lateinit var mTypePokemonAdapter: TypePokemonAdapter
    private val arrImg = ArrayList<Int>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.tv_pokemonName)
        val pokemonAvatar: ImageView = itemView.findViewById(R.id.img_avt)
        val pokemonID: TextView = itemView.findViewById(R.id.tv_pokemonID)
        val rvListType: RecyclerView = itemView.findViewById(R.id.rv_listTypePokemon)
    }

    fun setList(list: ArrayList<InformationPokemon>) {
        this.mListPokemon = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mTypePokemonAdapter = TypePokemonAdapter(mContext)
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_list_pokemon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return this.mListPokemon.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pokemonName.text = mListPokemon[position].name?.capitalize()
        if (mListPokemon[position].id != null) {
            when {
                mListPokemon[position].id!! < 10 -> {
                    holder.pokemonID.text = "#00${mListPokemon.get(position).id}"
                }
                mListPokemon[position].id!! < 100 -> {
                    holder.pokemonID.text = "#0${mListPokemon.get(position).id}"
                }
                else -> {
                    holder.pokemonID.text = "#${mListPokemon.get(position).id}"
                }
            }
        }

        Picasso.with(mContext)
            .load(mListPokemon[position].sprites?.other?.officialArtwork?.frontDefault)
            .into(holder.pokemonAvatar)

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        holder.rvListType.layoutManager = layoutManager
        holder.rvListType.adapter = mTypePokemonAdapter
        arrImg.clear()
        mListPokemon[position].types?.forEach {
            if (it.type?.name != null) {
                arrImg.add(Utility.nameToImage(it.type?.name!!))
            }
        }
        mTypePokemonAdapter.setList(arrImg)
        holder.itemView.setOnClickListener() {
            iListPokemonWithActivity.onItemClick(mListPokemon[position], position)
        }
    }

    interface IListPokemonWithActivity {
        fun onItemClick(informationPokemon: InformationPokemon, pos: Int)
    }
}