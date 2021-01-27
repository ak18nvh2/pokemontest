package com.example.pokemonapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.adapter.MoveAdapter
import com.example.pokemonapp.models.Move
import kotlinx.android.synthetic.main.fragment_moves.view.*

class MovesFragment() : Fragment() {

    private var mList: ArrayList<Move> = ArrayList()
    private var mImgType = -1
    var mAdapter: MoveAdapter? = null

    constructor(arrayListMove: ArrayList<Move>, imgType: Int) : this() {
        this.mList = arrayListMove
        this.mImgType = imgType
    }

    constructor(arrayListMove: ArrayList<Move>) : this() {
        this.mList = arrayListMove
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAdapter = MoveAdapter(requireActivity())
        return inflater.inflate(R.layout.fragment_moves, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_Moves.layoutManager = LinearLayoutManager(activity)
        view.rv_Moves.adapter = mAdapter
        mList.sortedWith(compareBy { it.versionGroupDetails?.get(0)?.levelLearnedAt })
        mAdapter?.setList(mList, mImgType)
    }

}