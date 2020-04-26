package com.jfalck.hopworld.ui.home

import androidx.lifecycle.MutableLiveData
import com.jfalck.hopworld.App
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.ui.HopWorldViewModel
import io.reactivex.schedulers.Schedulers

class HomeViewModel : HopWorldViewModel() {

    val beer: MutableLiveData<Beer?> = MutableLiveData()

    fun getBeer() {
        App.breweryRepository.getRandomBeer()
            ?.subscribeOn(Schedulers.io())
            ?.onErrorReturnItem(Beer.DEFAULT)
            ?.subscribe { newBeer ->
                if (newBeer != null && newBeer != Beer.DEFAULT) {
                    beer.postValue(newBeer)
                }
            }?.let {
                compositeDisposable.add(it)
            }
    }
}