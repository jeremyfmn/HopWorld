package com.jfalck.hopworld.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jfalck.hopworld.App
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.BeerStyle
import com.jfalck.hopworld.net.model.BeerStyle.CREATOR.DEFAULT
import com.jfalck.hopworld.ui.HopWorldViewModel
import io.reactivex.schedulers.Schedulers

class HomeViewModel : HopWorldViewModel() {

    val beers: MutableLiveData<MutableList<Beer>> = MutableLiveData(mutableListOf())

    fun getBeers() {
        App.breweryRepository.getBeersFromLocalDatabase()
            .subscribeOn(Schedulers.io())
            ?.onErrorReturnItem(listOf())
            ?.subscribe {
                setBeerStyle(it)
                if (it.size < 10) {
                    getRandomBeers()
                }
            }?.let {
                compositeDisposable.add(it)
            }
    }

    private fun setBeerStyle(beersList: List<Beer>) {
        beersList.forEach { beer ->
            beer.styleId?.let { beerStyleId ->
                App.breweryRepository.getBeerStyleById(beerStyleId.toInt())
                    .subscribeOn(Schedulers.io())
                    ?.onErrorReturnItem(DEFAULT)
                    ?.subscribe {
                        beer.style = it
                    }?.let {
                        compositeDisposable.add(it)
                    }
            }
        }
        beers.postValue(beersList.toMutableList())
    }

    private fun getRandomBeers() {
        for (i in 0 until 10) {
            App.breweryRepository.getRandomBeer()
                ?.subscribeOn(Schedulers.io())
                ?.onErrorReturnItem(Beer.DEFAULT)
                ?.subscribe { newBeer ->
                    if (newBeer != null && newBeer != Beer.DEFAULT) {
                        saveBeerToDatabase(newBeer)
                        getBeerDetail(newBeer.id)
                        val newBeerList = beers.value
                        newBeerList?.add(newBeer)
                        beers.postValue(newBeerList)
                    }
                }?.let {
                    compositeDisposable.add(it)
                }
        }
    }

    private fun saveBeerToDatabase(beer: Beer) {
        App.breweryRepository.insertBeer(beer).subscribe({
            Log.d("Brewery", "Beer saved in local database")
        }, {
            Log.e("Brewery", it.message, it)
        }).let {
            compositeDisposable.add(it)
        }
        beer.style?.let { beerStyle ->
            saveStyleToDatabase(beerStyle)
        }
    }

    private fun saveStyleToDatabase(style: BeerStyle) {
        App.breweryRepository.insertBeerStyle(style).subscribe({
            Log.d("Brewery", "Beer Style saved in local database")
        }, {
            Log.e("Brewery", it.message, it)
        }).let {
            compositeDisposable.add(it)
        }
    }

    private fun getBeerDetail(beerId: String) {
        App.breweryRepository.getBeerDetail(beerId)?.subscribeOn(Schedulers.io())
            ?.onErrorReturnItem(BeerDetail.DEFAULT)
            ?.subscribe {

            }?.let {
                compositeDisposable.add(it)
            }
    }
}
