package com.example.predictpopularityofgame.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class WithOutRatingFirestoreRepository {
    private val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getDeveloperList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithOutRating").collection("DeveloperList")
    }

    fun getPublisherList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithOutRating").collection("PublisherList")
    }

    fun getGenresList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithOutRating").collection("GenresList")
    }

    fun getCategoriesList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithOutRating").collection("CategoriesList")
    }

    fun getSteamspyTagsList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithOutRating").collection("SteamspyTagsList")
    }

}