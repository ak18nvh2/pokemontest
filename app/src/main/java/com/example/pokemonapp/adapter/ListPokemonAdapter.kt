package com.example.pokemonapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.commons.Utility.format
import com.example.pokemonapp.models.InformationPokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_pokemon.view.*
import kotlinx.android.synthetic.main.item_progressbar.view.*

class ListPokemonAdapter(
    var mContext: Context,
    var iListPokemonWithActivity: IListPokemonWithActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListPokemon = ArrayList<InformationPokemon>()
    var mIsLoadMore = false

    class ListPokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setList(list: ArrayList<InformationPokemon>) {
        this.mListPokemon = ArrayList(list)
        setLoadMoreItem(false)
        notifyDataSetChanged()
    }

    fun setLoadMoreItem(isLoadMore: Boolean) {
        mIsLoadMore = isLoadMore
        if (isLoadMore) {
            mListPokemon.add(InformationPokemon(itemViewType = Utility.KEY_SHOW_PROGRESSBAR))
        } else {
            mListPokemon.removeAll { it.itemViewType == Utility.KEY_SHOW_PROGRESSBAR }
        }
        notifyItemChanged(mListPokemon.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListPokemonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    if (viewType == Utility.KEY_SHOW_DATA) R.layout.item_list_pokemon else R.layout.item_progressbar,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return this.mListPokemon.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Utility.KEY_SHOW_DATA) {
            holder.setIsRecyclable(false)
            holder.itemView.tv_pokemonName.text = mListPokemon[position].name?.capitalize()
            if (mListPokemon[position].id != null) {
                holder.itemView.tv_pokemonID.text = mListPokemon[position].id!!.format()
            }
            Picasso.with(mContext)
                .load(mListPokemon[position].sprites?.other?.officialArtwork?.frontDefault)
                .placeholder(R.drawable.egg)
                .into(holder.itemView.img_avt)

            holder.itemView.setOnClickListener() {
                iListPokemonWithActivity.onItemClick(mListPokemon[position], position)
            }

            mListPokemon[position].types?.size?.let {
                when {
                    it == 1 -> {
                        mListPokemon[position].types?.get(0)?.type?.name?.let { it2 ->
                            holder.itemView.img_typeRight.setImageResource(
                                Utility.nameToImage(it2)
                            )
                        }
                    }
                    it == 2 -> {
                        mListPokemon[position].types?.get(0)?.type?.name?.let { it2 ->
                            holder.itemView.img_typeMid.setImageResource(
                                Utility.nameToImage(it2)
                            )
                        }
                        mListPokemon[position].types?.get(1)?.type?.name?.let { it2 ->
                            holder.itemView.img_typeRight.setImageResource(
                                Utility.nameToImage(it2)
                            )
                        }
                    }
                    it > 2 -> {
                        mListPokemon[position].types?.get(0)?.type?.name?.let { it2 ->
                            holder.itemView.img_typeLeft.setImageResource(
                                Utility.nameToImage(it2)
                            )
                        }
                        mListPokemon[position].types?.get(1)?.type?.name?.let { it2 ->
                            holder.itemView.img_typeMid.setImageResource(
                                Utility.nameToImage(it2)
                            )
                        }
                        holder.itemView.img_typeRight.setImageResource(
                            R.drawable.ic_baseline_more_horiz_24
                        )
                    }
                    else -> {

                    }
                }

            }

        } else {
            holder.itemView.pb_itemOfListPokemon.visibility = View.VISIBLE
        }
    }

    override fun getItemViewType(position: Int) = mListPokemon[position].itemViewType

    interface IListPokemonWithActivity {
        fun onItemClick(informationPokemon: InformationPokemon, pos: Int)
    }
}