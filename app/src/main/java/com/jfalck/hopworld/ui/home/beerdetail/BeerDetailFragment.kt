package com.jfalck.hopworld.ui.home.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jfalck.hopworld.R
import com.jfalck.hopworld.fadeIn
import com.jfalck.hopworld.makeGone
import com.jfalck.hopworld.makeVisible
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.Hop
import com.jfalck.hopworld.ui.home.adapter.HopsAdapter
import kotlinx.android.synthetic.main.beer_detail_fragment.*

class BeerDetailFragment : Fragment() {

    private val args: BeerDetailFragmentArgs by navArgs()

    private lateinit var beer: Beer

    private lateinit var beerDetailViewModel: BeerDetailViewModel

    private lateinit var hopsAdapter: HopsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.beer_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beerDetailViewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        initView()
    }

    private fun initView() {

        initHopsAdapter()

        beer = args.beer
        beer_name.text = beer.name

        beerDetailViewModel.hops.observeForever(this::updateHopsView)
        beerDetailViewModel.beerDetail.observeForever(this::initViewWithBeerDetail)

        beerDetailViewModel.getHops(beer.id)
        beerDetailViewModel.getBeerDetail(beer.id)
    }

    private fun updateHopsView(hops: List<Hop>) {
        val filteredList = hops.filter { hop -> hop.name != null }
        hopsAdapter.itemsList = filteredList
        if (filteredList.isNullOrEmpty()) {
            hops_title_container.makeGone()
            rv_hops.makeGone()
        } else {
            hops_title_container.makeVisible()
            rv_hops.makeVisible()
        }

    }

    private fun initHopsAdapter() {
        context?.let { context ->
            rv_hops.layoutManager = LinearLayoutManager(context)
            if (!::hopsAdapter.isInitialized) {
                hopsAdapter = HopsAdapter(context)
            }
            rv_hops.adapter = hopsAdapter
        }
    }

    private fun initViewWithBeerDetail(beerDetail: BeerDetail) {
        beerDetail.description.let {
            if (!it.isNullOrEmpty()) {
                tv_description.text = beerDetail.description
                description_title_container.fadeIn()
                tv_description.fadeIn()
            }
        }
        beer.style?.description.let {
            if (!it.isNullOrEmpty()) {
                tv_style_description.text = it
                style_description_title_container.fadeIn()
                tv_style_description.fadeIn()
            }
        }
    }

}
