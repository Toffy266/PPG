package com.example.predictpopularityofgame.util

import com.example.predictpopularityofgame.databinding.FormItemBinding
import com.example.predictpopularityofgame.usecase.FetchWithOutRatingDataFromDatabase
import com.example.predictpopularityofgame.usecase.FetchWithRatingDataFromDatabase

class ConvertInputForPredict() {
    private var fetchWithRatingDataFromDatabase = FetchWithRatingDataFromDatabase()
    private var fetchWithOutRatingDataFromDatabase = FetchWithOutRatingDataFromDatabase()
    private var genresData = genresData()
    private var categoriesData = categoriesData()
    private var tagsData = steamspyTagsData()
    private var publisherData = publisherData()
    private var developerData = developerData()
    private var inputs : ArrayList<Float> = arrayListOf()

    fun predictInput(
        genresList: ArrayList<String>,
        categoriesList: ArrayList<String>,
        tagsList: ArrayList<String>,
        formItemBinding: FormItemBinding
    ): FloatArray {

        with(formItemBinding) {
            // Achievement Input
            formAchievementInput.text.toString().toFloatOrNull()?.let { inputs.add(it) }

            if (Check.havingRating) {
                // Positive Rating Input
                formPositiveInput.text.toString().toFloatOrNull()?.let { inputs.add(it) }
                // Negative Rating Input
                formNegativeInput.text.toString().toFloatOrNull()?.let { inputs.add(it) }
            }

            // Average Playtime Input
            formAveragePlaytimeInput.text.toString().toFloatOrNull()
                ?.let { inputs.add(it) }

            // Median Playtime Input
            formMedianPlaytimeInput.text.toString().toFloatOrNull()
                ?.let { inputs.add(it) }

            // Price Input
            formPriceInput.text.toString().toFloatOrNull()?.let { inputs.add(it) }

            // Month Input

            (formReleaseDateInput.text.toString().substring(5, 7)).toFloatOrNull()?.let { inputs.add(it) }

            if (Check.havingRating) {
                // Year Input
                (formReleaseDateInput.text.toString().substring(0, 4)).toFloatOrNull()?.let { inputs.add(it) }
            }

            // Genres Input
            for (i in genresData) {
                if ( i in genresList || (i == "Other" && genresList.any { it !in genresData}) ) {
                    inputs.add(1.toFloat())
                } else {
                    inputs.add(0.toFloat())
                }
            }

            // Categories Input
            for (j in categoriesData) {
                if ( j in categoriesList || (j == "Other" && categoriesList.any { it !in categoriesData}) ) {
                    inputs.add(1.toFloat())
                } else {
                    inputs.add(0.toFloat())
                }
            }

            // Tags Game Input
            for (k in tagsData) {
                if ( k in tagsList || (k == "Other" && tagsList.any { it !in tagsData}) ) {
                    inputs.add(1.toFloat())
                } else {
                    inputs.add(0.toFloat())
                }
            }

            // Platforms Input
            if (formPlatformsLinuxInput.isChecked) {
                inputs.add(1.toFloat())
            } else {
                inputs.add(0.toFloat())
            }
            if (formPlatformsMacInput.isChecked) {
                inputs.add(1.toFloat())
            } else {
                inputs.add(0.toFloat())
            }
            if (formPlatformsWindowInput.isChecked) {
                inputs.add(1.toFloat())
            } else {
                inputs.add(0.toFloat())
            }

            // Publisher Input
            GlobalBox.publisher = formPublisherInput.text.toString()
            for (m in publisherData) {
                if ( m == (formPublisherInput.text.toString()) || (m == "Other" && (formPublisherInput.text.toString() !in publisherData)) ) {
                    inputs.add(1.toFloat())
                } else {
                    inputs.add(0.toFloat())
                }
            }

            // Developer Input
            GlobalBox.developer = formDeveloperInput.text.toString()
            for (n in developerData) {
                if ( n == (formDeveloperInput.text.toString()) || (n == "Other" && (formDeveloperInput.text.toString() !in developerData)) ) {
                    inputs.add(1.toFloat())
                } else {
                    inputs.add(0.toFloat())
                }
            }
        }

        return inputs.toFloatArray()
    }


    private fun genresData() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.genresData()
        } else {
            fetchWithOutRatingDataFromDatabase.genresData()
        }
    }

    private fun categoriesData() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.categoriesData()
        } else {
            fetchWithOutRatingDataFromDatabase.categoriesData()
        }
    }

    private fun steamspyTagsData() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.steamspyTagsData()
        } else {
            fetchWithOutRatingDataFromDatabase.steamspyTagsData()
        }
    }

    private fun publisherData(): ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.publisherData()
        } else {
            fetchWithOutRatingDataFromDatabase.publisherData()
        }
    }

    private fun developerData() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.developerData()
        } else {
            fetchWithOutRatingDataFromDatabase.developerData()
        }
    }

}