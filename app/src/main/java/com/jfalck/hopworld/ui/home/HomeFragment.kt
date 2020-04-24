package com.jfalck.hopworld.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jfalck.hopworld.MainActivity
import com.jfalck.hopworld.R
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.ui.home.adapter.BeerSuggestionAdapter
import com.jfalck.hopworld.utils.GoogleSignUtils
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), BeerSuggestionAdapter.IOnItemClick {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var beerSuggestionAdapter: BeerSuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            val googleAccount = GoogleSignUtils.getAccount(context)
            if (googleAccount != null) {
                text_home.text =
                    getString(R.string.hello_name, googleAccount.givenName)
                iv_avatar.clipToOutline = true
                Glide.with(this).load(googleAccount.photoUrl.toString()).into(iv_avatar)
            }
        }
        initView()
    }

    override fun onResume() {
        super.onResume()
        getBeers()
    }

    private fun initView() {
        context?.let { context ->
            rv_random_beers.layoutManager = LinearLayoutManager(context)
            if (!::beerSuggestionAdapter.isInitialized) {
                beerSuggestionAdapter = BeerSuggestionAdapter(context, this@HomeFragment)
            }
            rv_random_beers.adapter = beerSuggestionAdapter
        }
        if (beerSuggestionAdapter.itemsList.size != 10) {
            homeViewModel.beer.observeForever {
                it?.let {
                    beerSuggestionAdapter.addBeer(it)
                }
            }
        }
    }

    private fun getBeers() {
        if (activity is MainActivity) {
            for (i in 0 until 10) {
                homeViewModel.getBeer()
            }
        }
    }

    override fun onItemClicked(beer: Beer) {
        HomeFragmentDirections.actionNavigationHomeToNavigationBeerDetail(beer).let { action ->
            findNavController().navigate(action)
        }
    }
}
