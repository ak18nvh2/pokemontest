package com.example.pokemonapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.models.InformationPokemon
import com.squareup.picasso.Picasso

class ListPokemonAdapter(
    var mContext: Context,
    var iListPokemonWithActivity: IListPokemonWithActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var layoutManager: LinearLayoutManager? = null
    private var mListPokemon = ArrayList<InformationPokemon>()
    private lateinit var mTypePokemonAdapter: TypePokemonAdapter

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.tv_pokemonName)
        val pokemonAvatar: ImageView = itemView.findViewById(R.id.img_avt)
        val pokemonID: TextView = itemView.findViewById(R.id.tv_pokemonID)
        val rvListType: RecyclerView = itemView.findViewById(R.id.rv_listTypePokemon)
    }

    class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setList(list: ArrayList<InformationPokemon>) {
        this.mListPokemon = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mTypePokemonAdapter = TypePokemonAdapter(mContext)
        layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        return when (viewType) {
            Utility.KEY_SHOW_DATA -> {
                DataViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.item_list_pokemon, parent, false)
                )
            }
            else -> ProgressBarViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_progressbar, parent, false)
            )
        }

    }


    override fun getItemCount(): Int {
        return this.mListPokemon.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        if (getItemViewType(position) == Utility.KEY_SHOW_DATA) {
            if (holder is DataViewHolder) {
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
                    .placeholder(R.drawable.egg)
                    .into(holder.pokemonAvatar)
                holder.rvListType.layoutManager = layoutManager
                holder.rvListType.adapter = mTypePokemonAdapter
                val mArrImg = ArrayList<Int>()
                mListPokemon[position].types?.forEach {
                    if (it.type?.name != null) {
                        mArrImg.add(Utility.nameToImage(it.type?.name!!))
                    }
                }
                mTypePokemonAdapter.setList(mArrImg)
                holder.itemView.setOnClickListener() {
                    iListPokemonWithActivity.onItemClick(mListPokemon[position], position)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val viewType = mListPokemon[position].isLoad
        return if (viewType) {
            Utility.KEY_SHOW_PROGRESSBAR
        } else {
            Utility.KEY_SHOW_DATA
        }
    }

    interface IListPokemonWithActivity {
        fun onItemClick(informationPokemon: InformationPokemon, pos: Int)
    }
}