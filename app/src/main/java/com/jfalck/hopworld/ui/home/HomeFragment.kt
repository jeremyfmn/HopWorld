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
import com.google.firebase.auth.FirebaseUser
import com.jfalck.hopworld.MainActivity
import com.jfalck.hopworld.R
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.ui.home.adapter.BeerSuggestionAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), BeerSuggestionAdapter.IOnItemClick {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var beerSuggestionAdapter: BeerSuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        getBeers()
    }

    private fun initView() {
        iv_avatar.clipToOutline = true
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
        homeViewModel.firebaseUser.observeForever {
            updateUi(it)
        }
    }

    private fun updateUi(firebaseUser: FirebaseUser) {
        text_home.text =
            getString(R.string.hello_name, firebaseUser.displayName)
        Glide.with(this).load(firebaseUser.photoUrl.toString()).into(iv_avatar)
    }

    private fun getBeers() {
        if (activity is MainActivity) {
            for (i in 0 until 10) {
                homeViewModel.getBeer()
            }
        }
    }

    override fun onItemClicked(beer: Beer) =
        findNavController()
            .navigate(HomeFragmentDirections.actionNavigationHomeToNavigationBeerDetail(beer))
}
