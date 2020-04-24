package com.jfalck.hopworld.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jfalck.hopworld.App
import com.jfalck.hopworld.net.model.Beer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}