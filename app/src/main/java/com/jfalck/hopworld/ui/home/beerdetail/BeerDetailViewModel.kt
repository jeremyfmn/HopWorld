package com.jfalck.hopworld.ui.home.beerdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jfalck.hopworld.App
import com.jfalck.hopworld.net.model.Hop
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BeerDetailViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val hops: MutableLiveData<List<Hop>> = MutableLiveData()

    fun getHops(beerId: String) {
        App.breweryRepository.getHops(beerId)?.subscribeOn(Schedulers.io())?.onErrorReturnItem(
            listOf()
        )?.subscribe {
            hops.postValue(it)
        }?.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
