package com.example.predictpopularityofgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.predictpopularityofgame.adapter.DialogAdapter
import com.example.predictpopularityofgame.databinding.FragmentFormDialogBinding
import com.example.predictpopularityofgame.model.CheckBoxModel
import com.example.predictpopularityofgame.usecase.FetchWithOutRatingDataFromDatabase
import com.example.predictpopularityofgame.usecase.FetchWithRatingDataFromDatabase
import com.example.predictpopularityofgame.util.Check

class FormDialogFragment : DialogFragment() {
    private lateinit var fragmentFormDialogBinding : FragmentFormDialogBinding
    private var fetchWithRatingDataFromDatabase = FetchWithRatingDataFromDatabase()
    private var fetchWithOutRatingDataFromDatabase = FetchWithOutRatingDataFromDatabase()
    private var arrayList = ArrayList<CheckBoxModel>()
    private var genresSelected : ArrayList<String> = arrayListOf()
    private var categoriesSelected : ArrayList<String> = arrayListOf()
    private var tagsSelected : ArrayList<String> = arrayListOf()
    private var genresList = genresList()
    private var categoriesList = categoriesList()
    private var steamspyTagsList = steamspyTagsList()

    interface OnInputListener {
        fun genresSelected(genres: ArrayList<String>)
        fun categoriesSelected(categories : ArrayList<String>)
        fun tagsSelected(tags : ArrayList<String>)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fragmentFormDialogBinding = FragmentFormDialogBinding.inflate(layoutInflater)
        return fragmentFormDialogBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentFormDialogBinding) {
            if (tag == "Genres") {
                formDialogTitle.text = "Choose Genres"
                dialogRecycleView.adapter = DialogAdapter(checkSelected(genresList))
            } else if (tag == "Categories") {
                formDialogTitle.text = "Choose Categories"
                dialogRecycleView.adapter = DialogAdapter(checkSelected(categoriesList))
            } else {
                formDialogTitle.text = "Choose Tags"
                dialogRecycleView.adapter = DialogAdapter(checkSelected(steamspyTagsList))
            }


            confirmButton.setOnClickListener {

                for (x in arrayList) {
                    if (tag == "Genres" && x.isSelected) {
                        genresSelected.add(x.name)
                    }
                    if (tag == "Categories" && x.isSelected) {
                        categoriesSelected.add(x.name)
                    }
                    if (tag == "Tags" && x.isSelected) {
                        tagsSelected.add(x.name)
                    }
                }

                (parentFragment as OnInputListener).genresSelected(genresSelected)
                (parentFragment as OnInputListener).categoriesSelected(categoriesSelected)
                (parentFragment as OnInputListener).tagsSelected(tagsSelected)

                dialog?.dismiss()
            }

        }

    }

    private fun checkSelected(data: ArrayList<String>): ArrayList<CheckBoxModel> {
        for (i in data) {
            arrayList.add(CheckBoxModel(i, false))
        }
        return arrayList
    }

    private fun genresList() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.genresData()
        } else {
            fetchWithOutRatingDataFromDatabase.genresData()
        }
    }

    private fun categoriesList() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.categoriesData()
        } else {
            fetchWithOutRatingDataFromDatabase.categoriesData()
        }
    }

    private fun steamspyTagsList() : ArrayList<String> {
        return if (Check.havingRating) {
            fetchWithRatingDataFromDatabase.steamspyTagsData()
        } else {
            fetchWithOutRatingDataFromDatabase.steamspyTagsData()
        }
    }

}