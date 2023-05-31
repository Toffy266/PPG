package com.example.predictpopularityofgame.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class WithRatingFirestoreRepository {
    private val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getDeveloperList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithRating").collection("DeveloperList")
    }

    fun getPublisherList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithRating").collection("PublisherList")
    }

    fun getGenresList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithRating").collection("GenresList")
    }

    fun getCategoriesList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithRating").collection("CategoriesList")
    }

    fun getSteamspyTagsList(): CollectionReference {
        return firestoreDB.collection("PcGame").document("WithRating").collection("SteamspyTagsList")
    }

}