package com.example.predictpopularityofgame.usecase

import com.example.predictpopularityofgame.repository.WithOutRatingFirestoreRepository

class FetchWithOutRatingDataFromDatabase {
    private val withOutRatingFirestoreRepository = WithOutRatingFirestoreRepository()
    private var developerList : ArrayList<String> = arrayListOf()
    private var publisherList : ArrayList<String> = arrayListOf()
    private var genresList : ArrayList<String> = arrayListOf()
    private var categoriesList : ArrayList<String> = arrayListOf()
    private var steamspyTagsList : ArrayList<String> = arrayListOf()

    fun genresData(): ArrayList<String> {
        withOutRatingFirestoreRepository.getGenresList().get()
            .addOnSuccessListener { documents ->
                genresList.clear()
                for (document in documents) {
                    document.getString("genres")?.let { genresList.add(it.trim()) }
                }
            }

        return genresList
    }

    fun categoriesData(): ArrayList<String> {
        withOutRatingFirestoreRepository.getCategoriesList().get()
            .addOnSuccessListener { documents ->
                categoriesList.clear()
                for (document in documents) {
                    document.getString("categories")?.let { categoriesList.add(it.trim()) }
                }
            }

        return categoriesList
    }

    fun steamspyTagsData(): ArrayList<String> {
        withOutRatingFirestoreRepository.getSteamspyTagsList().get()
            .addOnSuccessListener { documents ->
                steamspyTagsList.clear()
                for (document in documents) {
                    document.getString("tags")?.let { steamspyTagsList.add(it.trim()) }
                }
            }

        return steamspyTagsList
    }

    fun developerData(): ArrayList<String> {
        withOutRatingFirestoreRepository.getDeveloperList().get()
            .addOnSuccessListener { documents ->
                developerList.clear()
                for (document in documents) {
                    document.getString("developers")?.let { developerList.add(it.trim()) }
                }
            }

        return developerList
    }

    fun publisherData(): ArrayList<String> {
        withOutRatingFirestoreRepository.getPublisherList().get()
            .addOnSuccessListener { documents ->
                publisherList.clear()
                for (document in documents) {
                    document.getString("publishers")?.let { publisherList.add(it.trim()) }
                }
            }

        return publisherList
    }
    
}