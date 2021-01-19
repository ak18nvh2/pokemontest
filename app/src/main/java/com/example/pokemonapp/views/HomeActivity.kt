package com.example.pokemonapp.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.pokemonapp.adapter.ListPokemonAdapter
import com.example.pokemonapp.R
import com.example.pokemonapp.Utility
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
        rv_listPokemon.layoutManager = LinearLayoutManager(this)
        rv_listPokemon.adapter = mListPokemonAdapter
        img_search.setOnClickListener(this)
        img_refresh.setOnClickListener(this)
        getListPokemon(0, 20)
    }

    private fun getListPokemon(offset: Int, limit: Int) {
        val call = RetrofitClient.instance.getListPokemon(offset, limit)
        mListPokemonViewModel.getListPokemon(call)
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_processbar)
        mDialog.show()
        mDialog.setCancelable(false)
        mDialog.btn_Cancel.setOnClickListener() {
            mDialog.dismiss()
            call.cancel()
        }
    }

    private fun registerLiveDataListener() {
        mListPokemonViewModel.listPokemon.observe(this, {
            mListPokemon = it
            mInformationPokemonViewModel.amountOfPokemon.value = 0
        })

        mInformationPokemonViewModel.aPokemon.observe(this, {
            if (mKeyShowInformationPokemon == Utility.KEY_DISPLAY) {
                mArrayListInformationPokemon.add(it)
                mInformationPokemonViewModel.getAPokemonNext()

            }
            else if (mKeyShowInformationPokemon == Utility.KEY_SEARCH) {
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

        mInformationPokemonViewModel.amountOfPokemon.observe(this, {
            if (mListPokemon.results?.size != null && it == mListPokemon.results?.size!!) {
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
                mInformationPokemonViewModel.noticeGetAllInformationPokemonSuccessful()
            } else {
                val call =
                    RetrofitClient.instance.getInformationAPokemon(mListPokemon.results?.get(it)?.name!!)
                mInformationPokemonViewModel.getAPokemon(call)
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
    }

    override fun onItemClick(informationPokemon: InformationPokemon, pos: Int) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search -> {
                closeKeyboard()
                mKeyShowInformationPokemon = Utility.KEY_SEARCH
                val call =
                    RetrofitClient.instance.getInformationAPokemon(edt_inputSearch.text.toString().toLowerCase())
                mInformationPokemonViewModel.getAPokemon(call)
                mDialog.show()
            }
            R.id.img_refresh -> {
                closeKeyboard()
                mKeyShowInformationPokemon = Utility.KEY_DISPLAY
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
                edt_inputSearch.text.clear()
            }
        }
    }
}