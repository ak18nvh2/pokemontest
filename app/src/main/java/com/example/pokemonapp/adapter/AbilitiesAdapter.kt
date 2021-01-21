package com.example.pokemonapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.models.InformationItemAbilities

class AbilitiesAdapter(var mContext: Context) :
    RecyclerView.Adapter<AbilitiesAdapter.ViewHolder>() {

    private var mArrayListAbilities = ArrayList<InformationItemAbilities>()
    private var mPrimaryColor = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgVisibility: ImageView = itemView.findViewById(R.id.img_visibility)
        val tvNameOfAbility: TextView = itemView.findViewById(R.id.tv_nameOfAbilities)
        val tvContentAbility: TextView = itemView.findViewById(R.id.tv_AbilitiesContent)
    }

    fun setList(list: ArrayList<InformationItemAbilities>, primaryColor: Int) {
        this.mArrayListAbilities = list
        this.mPrimaryColor = primaryColor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_abilities, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return mArrayListAbilities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNameOfAbility.text = mArrayListAbilities[position].nameAbility.capitalize()
        holder.tvContentAbility.text = mArrayListAbilities[position].contentAbility.capitalize()
        if (mArrayListAbilities[position].isHidden) {
            holder.imgVisibility.setImageResource(R.drawable.visibility)
            holder.imgVisibility.setColorFilter(
                mContext.resources.getColor(
                    mPrimaryColor,
                    mContext.theme
                )
            )
        }
        if (this.mPrimaryColor != -1) {
            holder.tvNameOfAbility.setTextColor(
                mContext.resources.getColor(
                    mPrimaryColor,
                    mContext.theme
                )
            )
        }
    }

}