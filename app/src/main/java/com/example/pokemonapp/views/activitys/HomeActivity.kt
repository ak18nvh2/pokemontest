package com.example.pokemonapp.views.activitys

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.pokemonapp.adapter.ListPokemonAdapter
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.api.RetrofitClient
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import com.example.pokemonapp.viewmodels.InformationPokemonViewModel
import com.example.pokemonapp.viewmodels.ListPokemonViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_processbar.*

class HomeActivity : AppCompatActivity(), ListPokemonAdapter.IListPokemonWithActivity,
    View.OnClickListener {
    private lateinit var mListPokemonViewModel: ListPokemonViewModel
    private lateinit var mInformationPokemonViewModel: InformationPokemonViewModel
    private lateinit var mListPokemon: ListPokemon
    private lateinit var mListPokemonAdapter: ListPokemonAdapter
    private var mArrayListInformationPokemon = ArrayList<InformationPokemon>()
    private var mKeyShowInformationPokemon = 2
    private lateinit var mDialog: MaterialDialog
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        registerLiveDataListener()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun init() {
        mListPokemonViewModel = ListPokemonViewModel()
        mInformationPokemonViewModel = InformationPokemonViewModel()
        mListPokemon = ListPokemon()
        mListPokemonAdapter = ListPokemonAdapter(this, this)
        mLinearLayoutManager = LinearLayoutManager(this)
        rv_listPokemon.layoutManager = mLinearLayoutManager
        rv_listPokemon.adapter = mListPokemonAdapter
        loadMore()
        img_search.setOnClickListener(this)
        img_refresh.setOnClickListener(this)
        getListPokemon(0, 20)
    }

    private fun loadMore() {
        rv_listPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && !mListPokemonAdapter.mIsLoadMore) {
                    if (mKeyShowInformationPokemon == Utility.KEY_DISPLAY) {
                        if (mListPokemon.next != null) {
                            mListPokemonViewModel.getNextListPokemon(mListPokemon.next!!)
                            mListPokemonAdapter.setLoadMoreItem(true)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "This is end of list Pokemon",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })
    }

    private fun getListPokemon(offset: Int, limit: Int) {
        mListPokemonViewModel.getListPokemon(offset, limit)
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_processbar)
        mDialog.show()
        mDialog.setCancelable(false)
        mDialog.btn_Cancel.setOnClickListener() {
            mDialog.dismiss()
            mListPokemonViewModel.callGet?.cancel()
        }
    }

    private fun registerLiveDataListener() {
        mListPokemonViewModel.listPokemon.observe(this, {
            if (it != null) {
                mListPokemon = it
                mInformationPokemonViewModel.amountOfPokemon.value = 0
            } else {
                mDialog.dismiss()
            }
        })

        mInformationPokemonViewModel.amountOfPokemon.observe(this, {
            if (mListPokemon.results?.size != null && it == mListPokemon.results?.size!!) {
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
                mDialog.dismiss()
            } else {
                val call =
                    RetrofitClient.instance.getInformationAPokemon(mListPokemon.results?.get(it)?.name!!)
                mInformationPokemonViewModel.getAPokemon(call)
            }
        })

        mInformationPokemonViewModel.aPokemon.observe(this, {
            if (mKeyShowInformationPokemon == Utility.KEY_DISPLAY) {
                if (it != null) {
                    mArrayListInformationPokemon.add(it)
                    mInformationPokemonViewModel.getAPokemonNext()
                } else {
                    mDialog.dismiss()
                    mListPokemonAdapter.setList(mArrayListInformationPokemon)
                }

            } else if (mKeyShowInformationPokemon == Utility.KEY_SEARCH) {
                mDialog.dismiss()
                if (it == null) {
                    val arrayListSearchPokemon = arrayListOf<InformationPokemon>()
                    mListPokemonAdapter.setList(arrayListSearchPokemon)
                    Toast.makeText(
                        this,
                        "Can't find a pokemon has id or name is " + edt_inputSearch.text,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val arrayListSearchPokemon = arrayListOf(it)
                    mListPokemonAdapter.setList(arrayListSearchPokemon)
                    mInformationPokemonViewModel.noticeGetAllInformationPokemonSuccessful()
                }
            }
        })


        mInformationPokemonViewModel.notification.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            mDialog.dismiss()
        })

        mListPokemonViewModel.notification.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            mDialog.dismiss()
        })
        mInformationPokemonViewModel.isSearching.observe(this, {
            mKeyShowInformationPokemon = Utility.KEY_SEARCH
            mDialog.show()
            closeKeyboard()

        })
    }

    override fun onItemClick(informationPokemon: InformationPokemon, pos: Int) {
        val intent = Intent(this, DetailPokemonActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Utility.KEY_BUNDLE_INFORMATION_POKEMON, informationPokemon)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search -> {
                mInformationPokemonViewModel.searchAPokemon(edt_inputSearch.text.toString())
            }
            R.id.img_refresh -> {
                closeKeyboard()
                mKeyShowInformationPokemon = Utility.KEY_DISPLAY
                mArrayListInformationPokemon.clear()
                getListPokemon(0, 20)
                edt_inputSearch.text.clear()
            }
        }
    }
}