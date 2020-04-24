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
import com.jfalck.hopworld.makeGone
import com.jfalck.hopworld.makeVisible
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.ui.home.adapter.HopsAdapter
import kotlinx.android.synthetic.main.beer_detail_fragment.*

class BeerDetailFragment : Fragment() {

    private val args: BeerDetailFragmentArgs by navArgs()

    private lateinit var beer: Beer

    private lateinit var viewModel: BeerDetailViewModel

    private lateinit var hopsAdapter: HopsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.beer_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        initViewModel()
    }

    private fun initView() {
        beer = args.beer
        beer_name.text = beer.name
        tv_style_title.text = beer.style?.name
        tv_description.text = beer.style?.description

        context?.let { context ->
            rv_hops.layoutManager = LinearLayoutManager(context)
            if (!::hopsAdapter.isInitialized) {
                hopsAdapter = HopsAdapter(context)
            }
            rv_hops.adapter = hopsAdapter
        }
    }

    private fun initViewModel() {
        viewModel.hops.observeForever {
            val filteredList = it.filter { hop -> hop.name != null }
            hopsAdapter.itemsList = filteredList
            if (filteredList.isNullOrEmpty()) {
                hops_title_container.makeGone()
                rv_hops.makeGone()
            } else {
                hops_title_container.makeVisible()
                rv_hops.makeVisible()
            }
        }
        viewModel.getHops(beer.id)
    }

}
