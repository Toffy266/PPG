package com.example.predictpopularityofgame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.predictpopularityofgame.databinding.PredictResultFeatureItemChildBinding
import com.example.predictpopularityofgame.util.GlobalBox

class FeatureAdapter(
    private var featureList: ArrayList<String>
) : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PredictResultFeatureItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PredictResultFeatureItemChildBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return featureList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, positon: Int) {
        val viewItem = featureList[positon]

        val textFeature = arrayListOf<String>("Genres", "Categories", "Steamspy_tags", "Platforms", "Publisher", "Developer")
        with(viewHolder){
            binding.apply {
                if ( textFeature.any { viewItem.contains(it, ignoreCase = true) } ) {
                    featureButton.text = fixWords(viewItem)
                } else {
                    featureButton.text = capitalizeWords(viewItem)
                }

            }
        }

    }

    private fun capitalizeWords(str: String): String {
        val pattern = Regex("\\.0?\\s")
        val newStr = (str.replace("_", " "))
                        .split(' ')
                        .joinToString(" ") {
                            it.replaceFirstChar(Char::uppercaseChar)
                        }

        return if ( str.contains("Year", ignoreCase = true) || str.contains("Month", ignoreCase = true) ) {
            pattern.replace(newStr, " ")
        } else {
            newStr
        }

    }

    private fun fixWords(str: String): String {
        val pattern = Regex(":\\s\\w\\.0?\\s")
        val patternGenres = Regex("^Genres_")
        val patternCategories= Regex("^Categories_")
        val patternTags = Regex("^Steamspy_tags_")
        val patternPlatform = Regex("^Platforms_")
        val patternPublisher = Regex("^Publisher_")
        val patternDeveloper = Regex("^Developer_")

        return if ( str.contains("Genres", ignoreCase = true)) {
            pattern.replace(patternGenres.replace(str, "Genres: "), " ")
        } else if (str.contains("Categories", ignoreCase = true)) {
            pattern.replace(patternCategories.replace(str,"Categories: "), " ")
        } else if (str.contains("Steamspy_tags", ignoreCase = true)) {
            pattern.replace(patternTags.replace(str,"Game Tags: "), " ")
        } else if (str.contains("Platforms", ignoreCase = true)) {
            pattern.replace(patternPlatform.replace(str,"Platforms: "), " ")
        } else if (str.contains("Publisher", ignoreCase = true)) {
            pattern.replace(patternPublisher.replace(
                (str).replace("Other", GlobalBox.publisher),"Publisher: "), " ")
        } else {
            pattern.replace(patternDeveloper.replace(
                (str).replace("Other", GlobalBox.developer), "Developer: "), " ")
        }

    }

}