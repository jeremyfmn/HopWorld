package com.jfalck.hopworld.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jfalck.hopworld.App
import com.jfalck.hopworld.data.BeerTypes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {

    var beerTypesLikedLiveData: MutableLiveData<MutableList<BeerTypes>> =
        MutableLiveData(mutableListOf())

    private val compositeDisposable = CompositeDisposable()

    init {
        App.breweryRepository.getBeerTypesLiked().subscribeOn(Schedulers.io())?.onErrorReturnItem(
            listOf()
        )?.subscribe {
            beerTypesLikedLiveData.postValue(
                BeerTypes.fromIdsList(it)?.toMutableList() ?: mutableListOf()
            )
        }?.let {
            compositeDisposable.add(it)
        }
    }

    fun updateLikedBeers(
        beerType: BeerTypes,
        isForDeletion: Boolean = false
    ) {
        beerTypesLikedLiveData.value?.let { beerTypes ->
            if (isForDeletion) {
                beerTypes.remove(beerType)
            } else {
                beerTypes.add(beerType)
            }
            App.breweryRepository.saveLikedBeers(beerTypes.distinct())
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}