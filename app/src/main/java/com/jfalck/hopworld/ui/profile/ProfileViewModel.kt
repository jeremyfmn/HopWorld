package com.jfalck.hopworld.ui.profile

import androidx.lifecycle.MutableLiveData
import com.jfalck.hopworld.App
import com.jfalck.hopworld.data.BeerTypes
import com.jfalck.hopworld.ui.HopWorldViewModel
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : HopWorldViewModel() {

    var beerTypesLikedLiveData: MutableLiveData<MutableList<BeerTypes>> =
        MutableLiveData(mutableListOf())

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
}