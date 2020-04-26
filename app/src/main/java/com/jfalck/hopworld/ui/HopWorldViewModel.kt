package com.jfalck.hopworld.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.jfalck.hopworld.App
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class HopWorldViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    val firebaseUser: MutableLiveData<FirebaseUser> = MutableLiveData()

    init {
        Observable.just(App.breweryRepository.firebaseUser).subscribe {
            it?.let {
                firebaseUser.value = it
            }
        }.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}