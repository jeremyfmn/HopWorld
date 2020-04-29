package com.jfalck.hopworld.net.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.jfalck.hopworld.App.Companion.breweryService
import com.jfalck.hopworld.data.BeerTypes
import com.jfalck.hopworld.data.db.BeerDao
import com.jfalck.hopworld.data.db.BeerDatabase
import com.jfalck.hopworld.data.db.BeerDetailDao
import com.jfalck.hopworld.data.db.BeerStyleDao
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.BeerDetail
import com.jfalck.hopworld.net.model.BeerStyle
import com.jfalck.hopworld.net.model.Hop
import io.reactivex.Completable
import io.reactivex.Observable

class BreweryRepository {

    var firebaseUser: FirebaseUser? = null

    private var firebaseDB: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val service = breweryService

    private lateinit var beerDao: BeerDao
    private lateinit var beerStyleDao: BeerStyleDao
    private lateinit var beerDetailDao: BeerDetailDao

    fun initDaos(context: Context) {
        beerDao = BeerDatabase.getInstance(context).beerDao()
        beerStyleDao = BeerDatabase.getInstance(context).beerStyleDao()
        beerDetailDao = BeerDatabase.getInstance(context).beerDetailDao()
    }

    fun getRandomBeer(): Observable<Beer>? =
        breweryService.getRandomBeer().map {
            val beer = it.data
            beerDao.insertBeer(beer)
            beer
        }

    fun getHops(id: String): Observable<List<Hop>>? =
        breweryService.getBeerHops(id).map {
            it.data
        }

    fun saveLikedBeers(beerTypes: List<BeerTypes>) {
        val user = hashMapOf(
            "id" to firebaseUser?.uid,
            "email" to firebaseUser?.email,
            "givenName" to firebaseUser?.displayName,
            "beersLiked" to beerTypes.map { it.id }
        )

        firebaseUser?.uid?.let { id ->
            firebaseDB.collection("users").document(id)
                .set(user)
                .addOnSuccessListener {
                    Log.d("Firestore", "DocumentSnapshot added with ID: $id")
                }.addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }
    }

    fun getBeerTypesLiked(): Observable<List<Int>> {
        return Observable.create { emitter ->
            firebaseUser?.uid?.let { id ->
                firebaseDB.collection("users").document(id)
                    .addSnapshotListener { snapshot, firebaseFirestoreException ->
                        val result = snapshot?.data?.get("beersLiked") as List<Int>?
                        if (result == null || firebaseFirestoreException != null) {
                            emitter.onError(Throwable("data retrieved is null"))
                        } else {
                            emitter.onNext(result)
                        }
                    }
            }
        }
    }

    fun insertBeer(beer: Beer): Completable =
        beerDao.insertBeer(beer)

    fun insertBeerStyle(beerStyle: BeerStyle): Completable =
        beerStyleDao.insertBeerStyle(beerStyle)

    fun getBeerStyleById(id: Int): Observable<BeerStyle> =
        beerStyleDao.getBeerStyleById(id)

    fun getBeersFromLocalDatabase(): Observable<List<Beer>> =
        beerDao.getBeers()

    fun getBeerDetail(beerId: String): Observable<BeerDetail> =
        service.getBeerDetails(beerId).map {
            val beerDetail = it.data
            insertBeerDetail(beerDetail)
            beerDetail
        }

    private fun insertBeerDetail(beerDetail: BeerDetail) =
        beerDetailDao.insertBeerDetail(beerDetail)

    fun clearDatabase(): Observable<Boolean> =
        Observable.create<Boolean> {
            beerDao.deleteAllBeers()
            beerStyleDao.deleteAllBeerStyles()
            beerDetailDao.deleteAllBeerDetails()
        }
}