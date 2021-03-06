package com.jfalck.hopworld.net.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.jfalck.hopworld.App.Companion.breweryService
import com.jfalck.hopworld.data.BeerTypes
import com.jfalck.hopworld.data.db.BeerDao
import com.jfalck.hopworld.data.db.BeerDatabase
import com.jfalck.hopworld.net.model.Beer
import com.jfalck.hopworld.net.model.Hop
import io.reactivex.Observable

class BreweryRepository {

    lateinit var userAccount: GoogleSignInAccount
    var firebaseDB: FirebaseFirestore = FirebaseFirestore.getInstance()

    val service = breweryService

    private lateinit var beerDao: BeerDao

    fun initDaos(context: Context) {
        beerDao = BeerDatabase.getInstance(context).beerDao()
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
            "id" to userAccount.id,
            "email" to userAccount.email,
            "givenName" to userAccount.givenName,
            "familyName" to userAccount.familyName,
            "beersLiked" to beerTypes.map { it.id }
        )

        userAccount.id?.let { id ->
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
            userAccount.id?.let {
                firebaseDB.collection("users").document(it)
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
}