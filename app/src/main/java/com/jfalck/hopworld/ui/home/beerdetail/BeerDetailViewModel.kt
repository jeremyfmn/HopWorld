package com.jfalck.hopworld.ui.home.beerdetail

import androidx.lifecycle.LiveData
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
        App.breweryRepository.getHops(beerId)?.subscribeOn(Schedulers.io())?.onErrorReturnItem(
            listOf()
        )?.subscribe {
            hops.postValue(it)
        }?.let {
            compositeDisposable.add(it)
        }
    }

    fun getBeerDetail(beerId: String): LiveData<BeerDetail> {
        val liveData = MutableLiveData<BeerDetail>()
        App.breweryRepository.getBeerDetail(beerId)?.subscribeOn(Schedulers.io())
            ?.onErrorReturnItem(BeerDetail.DEFAULT)
            ?.subscribe {
                liveData.postValue(it)
            }?.let {
                compositeDisposable.add(it)
            }
        return liveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
