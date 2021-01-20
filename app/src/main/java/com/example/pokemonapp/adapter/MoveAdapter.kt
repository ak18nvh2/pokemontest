package com.example.pokemonapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.models.Move

class MoveAdapter (var mContext: Context) : RecyclerView.Adapter<MoveAdapter.ViewHolder>() {

    private var list = ArrayList<Move>()
    private var imgType = -1
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgType: ImageView = itemView.findViewById(R.id.img_typeOfMove)
        val name: TextView = itemView.findViewById(R.id.tv_nameOfMove)
        val level: TextView = itemView.findViewById(R.id.tv_levelLearn)
    }
    fun  setList(list : ArrayList<Move>, imgType: Int){
        this.list = list
        this.imgType = imgType
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val homeView: View = layoutInflater.inflate(R.layout.item_moves, parent, false)
        return ViewHolder(homeView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].move?.name?.capitalize()
        holder.imgType.setImageResource(this.imgType)
        holder.level.text = "Level ${list[position].versionGroupDetails?.get(0)?.levelLearnedAt.toString()}"
    }

}