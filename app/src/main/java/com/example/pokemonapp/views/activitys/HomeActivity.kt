package com.example.pokemonapp.views.activitys

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.pokemonapp.adapter.ListPokemonAdapter
import com.example.pokemonapp.R
import com.example.pokemonapp.commons.Utility
import com.example.pokemonapp.commons.Utility.Call_API
import com.example.pokemonapp.models.InformationPokemon
import com.example.pokemonapp.models.ListPokemon
import com.example.pokemonapp.viewmodels.InformationPokemonViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_processbar.*
import retrofit2.Call

class HomeActivity : AppCompatActivity(), ListPokemonAdapter.IListPokemonWithActivity,
    View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mInformationPokemonViewModel: InformationPokemonViewModel
    private var mListPokemon: ListPokemon = ListPokemon()
    private lateinit var mListPokemonAdapter: ListPokemonAdapter
    private var mArrayListInformationPokemon = ArrayList<InformationPokemon>()
    private var mKeyShowInformationPokemon = Utility.KEY_DISPLAY
    private lateinit var mDialog: MaterialDialog
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var callGetAPokemon: Call<InformationPokemon>
    private lateinit var callGetListPokemon: Call<ListPokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerViewPokemon()
        registerLiveDataListener()
        initView()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initView() {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_processbar)
        mDialog.window?.setDimAmount(0F)
        mDialog.setCancelable(false)
        mDialog.btn_Cancel.setOnClickListener() {
            callGetAPokemon.cancel()
            callGetListPokemon.cancel()
            mDialog.dismiss()
        }
        srf_RecycleViewPokemon.setOnRefreshListener(this)
        img_search.setOnClickListener(this)
        img_refresh.setOnClickListener(this)
        getFirstListPokemon()
    }

    private fun dismissDialog() {
        mDialog.dismiss()
        lo_home.alpha = 1F
        img_refresh.isEnabled = true
    }

    private fun initRecyclerViewPokemon() {
        mListPokemonAdapter = ListPokemonAdapter(this, this)
        mLinearLayoutManager = LinearLayoutManager(this)
        rv_listPokemon.layoutManager = mLinearLayoutManager
        rv_listPokemon.adapter = mListPokemonAdapter
        rv_listPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && !mListPokemonAdapter.mIsLoadMore) {
                    if (mKeyShowInformationPokemon == Utility.KEY_DISPLAY) {
                        if (mListPokemon.next != null) {
                            mInformationPokemonViewModel.getNextListPokemon(mListPokemon.next!!)
                            mListPokemonAdapter.setLoadMoreItem(true)
                        }
                    }
                }
            }
        })
    }

    private fun getFirstListPokemon() {
        callGetListPokemon = Call_API.getListPokemon(0, 20)
        mInformationPokemonViewModel.getListPokemon(callGetListPokemon)
        lo_home.alpha = 0.2F
        mDialog.show()
    }

    private fun registerLiveDataListener() {
        mInformationPokemonViewModel = InformationPokemonViewModel()
        mInformationPokemonViewModel.listPokemon.observe(this, {
            if (it != null) {
                mListPokemon = ListPokemon(it)
                mInformationPokemonViewModel.amountOfPokemon.value = 0
            } else {
                dismissDialog()
            }
        })

        mInformationPokemonViewModel.amountOfPokemon.observe(this, {
            if (mListPokemon.results?.size != null && it == mListPokemon.results?.size!!) {
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
                dismissDialog()
            } else if (it <= -1) {
                mArrayListInformationPokemon.clear()
                mListPokemonAdapter.setList(mArrayListInformationPokemon)
            } else if (it < mListPokemon.results?.size!!) {
                callGetAPokemon =
                    Call_API.getInformationAPokemon(mListPokemon.results?.get(it)?.name!!)
                mInformationPokemonViewModel.getAPokemon(callGetAPokemon)
            }
        })

        mInformationPokemonViewModel.aPokemon.observe(this, {
            if (mKeyShowInformationPokemon == Utility.KEY_DISPLAY) {
                if (it != null) {
                    mArrayListInformationPokemon.add(it)
                    mInformationPokemonViewModel.getAPokemonNext()
                } else {
                    dismissDialog()
                }
            } else if (mKeyShowInformationPokemon == Utility.KEY_SEARCH) {
                dismissDialog()
                if (it == null) {
                    mListPokemonAdapter.setList(
                        arrayListOf(
                            InformationPokemon(
                                searchResult = "Can't find a pokemon has id or name is\n${edt_inputSearch.text}"
                            )
                        )
                    )
                } else {
                    mListPokemonAdapter.setList(arrayListOf(it))
                    mInformationPokemonViewModel.noticeGetAllInformationPokemonSuccessful()
                }
            }
        })

        mInformationPokemonViewModel.notification.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            dismissDialog()
        })

        mInformationPokemonViewModel.isSearching.observe(this, {
            mKeyShowInformationPokemon = Utility.KEY_SEARCH
            dismissDialog()
            closeKeyboard()
        })
    }

    override fun onItemClick(informationPokemon: InformationPokemon, pos: Int) {
        if (informationPokemon.id != null) {
            val intent = Intent(this, DetailPokemonActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Utility.KEY_BUNDLE_INFORMATION_POKEMON, informationPokemon)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else {
            Toast.makeText(this, "Can't see detail of this pokemon!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search -> {
                mInformationPokemonViewModel.searchAPokemon(edt_inputSearch.text.toString())
            }
        }
    }

    override fun onRefresh() {
        callGetListPokemon.cancel()
        callGetAPokemon.cancel()
        closeKeyboard()
        mListPokemon = ListPokemon()
        mInformationPokemonViewModel.amountOfPokemon.value = -1
        mKeyShowInformationPokemon = Utility.KEY_DISPLAY
        Handler().postDelayed({
            getFirstListPokemon()
        }, 100)
        edt_inputSearch.text.clear()
        srf_RecycleViewPokemon.isRefreshing = false
    }
}