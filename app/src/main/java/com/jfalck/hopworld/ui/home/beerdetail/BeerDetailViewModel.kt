package com.jfalck.hopworld.ui.home.beerdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jfalck.hopworld.App
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.Hop
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BeerDetailViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val hops: MutableLiveData<List<Hop>> = MutableLiveData()
    val beerDetail: MutableLiveData<BeerDetail> = MutableLiveData()

    fun getHops(beerId: String) {
        App.breweryRepository.getHops(beerId)?.subscribeOn(Schedulers.io())?.subscribe(
            { hops.postValue(it) },
            { Log.e("Brewery", it.message) }
        )?.let { compositeDisposable.add(it) }
    }

    fun getBeerDetail(beerId: String) {
        App.breweryRepository.getBeerDetail(beerId).subscribeOn(Schedulers.io())?.subscribe(
            { beerDetail.postValue(it) },
            { Log.e("Brewery", it.message) }
        )?.let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
