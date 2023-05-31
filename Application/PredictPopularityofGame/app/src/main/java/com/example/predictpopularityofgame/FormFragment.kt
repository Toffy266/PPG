package com.example.predictpopularityofgame

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.predictpopularityofgame.adapter.CategoriesAdapter
import com.example.predictpopularityofgame.adapter.GenresAdapter
import com.example.predictpopularityofgame.adapter.TagsAdapter
import com.example.predictpopularityofgame.databinding.FragmentFormBinding
import com.example.predictpopularityofgame.model.FeatureAffectModel
import com.example.predictpopularityofgame.model.PredictInputModel
import com.example.predictpopularityofgame.repository.RetrofitAPI
import com.example.predictpopularityofgame.usecase.FetchWithOutRatingDataFromDatabase
import com.example.predictpopularityofgame.usecase.FetchWithRatingDataFromDatabase
import com.example.predictpopularityofgame.util.Check
import com.example.predictpopularityofgame.util.ConvertInputForPredict
import com.example.predictpopularityofgame.util.getLoading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class FormFragment : Fragment(), FormDialogFragment.OnInputListener {
    private lateinit var fragmentFormBinding : FragmentFormBinding
    private var fetchWithRatingDataFromDatabase = FetchWithRatingDataFromDatabase()
    private var fetchWithOutRatingDataFromDatabase = FetchWithOutRatingDataFromDatabase()
    private var myCalendar : Calendar = Calendar.getInstance()
    private var genresList : ArrayList<String> = arrayListOf("+ Genres")
    private var categoriesList : ArrayList<String> = arrayListOf("+ Categories")
    private var tagsList : ArrayList<String> = arrayListOf("+ Tags")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fragmentFormBinding = FragmentFormBinding.inflate(layoutInflater)
        return fragmentFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkRating()
        getCalendar()
        showSelectedData()

        with(fragmentFormBinding.formItem) {

            if (Check.havingRating) {
                (fetchWithRatingDataFromDatabase).apply {
                    steamspyTagsData()

                    val adapterDeveloper: ArrayAdapter<String>? = context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            developerData()
                        )
                    }
                    formDeveloperInput.setAdapter(adapterDeveloper)

                    val adapterPublisher: ArrayAdapter<String>? = context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            publisherData()
                        )
                    }
                    formPublisherInput.setAdapter(adapterPublisher)
                }
            } else {
                (fetchWithOutRatingDataFromDatabase).apply {
                    steamspyTagsData()

                    val adapterDeveloper: ArrayAdapter<String>? = context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            developerData()
                        )
                    }
                    formDeveloperInput.setAdapter(adapterDeveloper)

                    val adapterPublisher: ArrayAdapter<String>? = context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            publisherData()
                        )
                    }
                    formPublisherInput.setAdapter(adapterPublisher)
                }
            }

            val convertInputForPredict = ConvertInputForPredict()
            val retrofitAPI = RetrofitAPI.getInstance()
            val dialog = getLoading()
            val handler = Handler()

            startPredictButton.setOnClickListener {
                if (checkEmptyInput()) {
                    val predictInput = convertInputForPredict.predictInput(genresList, categoriesList, tagsList, this)
                    val call: Call<FeatureAffectModel> = if(Check.havingRating) {
                        retrofitAPI.getFeatureRating(PredictInputModel("input", predictInput))
                    } else {
                        retrofitAPI.getFeatureNonRating(PredictInputModel("input", predictInput))
                    }

                    dialog.show()
                    handler.postDelayed( { // Close dialog after 15000ms
                        dialog.dismiss()

                        call.enqueue(object : Callback<FeatureAffectModel> {
                            override fun onResponse(call: Call<FeatureAffectModel>, response: Response<FeatureAffectModel>) {
                                findNavController().navigate(
                                    FormFragmentDirections.actionFormFragmentToResultPredictFragment(
                                        response.body()!!,
                                        formGameInput.text.toString()
                                    )
                                )
                            }
                            override fun onFailure(call: Call<FeatureAffectModel>, t: Throwable) {
                                Log.d(TAG, t.toString())
                            }
                        })

                    }, 15000)

                }
            }
        }

    }

    // Check Rating?
    private fun checkRating() {
        if (!Check.havingRating) {
            with(fragmentFormBinding.formItem) {
                formPositiveText.isVisible = false
                formPositiveInput.isVisible = false
                formNegativeText.isVisible = false
                formNegativeInput.isVisible = false
            }
        }
    }

    // Calendar
    private fun getCalendar() {
        val date =
            OnDateSetListener { _, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        fragmentFormBinding.formItem.formReleaseDateInput.setOnClickListener {
            context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        fragmentFormBinding.formItem.formReleaseDateInput.setText(dateFormat.format(myCalendar.time))
    }

    // Show Selected Data
    private  fun showSelectedData() {
        with(fragmentFormBinding.formItem) {
            genresRecycleView.adapter = GenresAdapter(genresList, this@FormFragment)
            categoriesRecycleView.adapter = CategoriesAdapter(categoriesList, this@FormFragment)
            tagsRecycleView.adapter = TagsAdapter(tagsList, this@FormFragment)
        }
    }

    override fun genresSelected(genres: ArrayList<String>) {
        if (genres.size != 0) {
            genresList = genres
            showSelectedData()
        }
    }

    override fun categoriesSelected(categories: ArrayList<String>) {
        if (categories.size != 0) {
            categoriesList = categories
            showSelectedData()
        }
    }

    override fun tagsSelected(tags: ArrayList<String>) {
        if (tags.size != 0) {
            tagsList = tags
            showSelectedData()
        }
    }

    // CheckEmptyInput
    private fun checkEmptyInput(): Boolean {
        var checkEmpty = true
        
        with(fragmentFormBinding.formItem) {
            // Game Name Input
            val gameInput = formGameInput.text.toString()
            // Developer Input
            val developerInput = formDeveloperInput.text.toString()
            // Publisher Input
            val publisherInput = formPublisherInput.text.toString()
            // Achievement Input
            val achievementInput = formAchievementInput.text.toString()
            // Positive Rating Input
            val positiveInput = formPositiveInput.text.toString()
            // Negative Rating Input
            val negativeInput = formNegativeInput.text.toString()
            // Average Playtime Input
            val averagePlaytimeInput = formAveragePlaytimeInput.text.toString()
            // Median Playtime Input
            val medianPlaytimeInput = formMedianPlaytimeInput.text.toString()
            // Price Input
            val priceInput = formPriceInput.text.toString()
            // Release Date Input
            val releaseDateInput = formReleaseDateInput.text.toString()
            // Platforms Input : Mac, Linux, Window
            val platformsMacInput= formPlatformsMacInput.isChecked
            val platformsLinuxInput = formPlatformsLinuxInput.isChecked
            val platformsWindowInput = formPlatformsWindowInput.isChecked

            if (TextUtils.isEmpty(gameInput) && gameInput == ""){
                formGameInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(developerInput) && developerInput == ""){
                formDeveloperInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(publisherInput) && publisherInput == ""){
                formPublisherInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(achievementInput) && achievementInput == ""){
                formAchievementInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (Check.havingRating) {
                if (TextUtils.isEmpty(positiveInput) && positiveInput == "") {
                    formPositiveInput.error = "Please fill out this filed."
                    checkEmpty = false
                }
                if (TextUtils.isEmpty(negativeInput) && negativeInput == "") {
                    formNegativeInput.error = "Please fill out this filed."
                    checkEmpty = false
                }
            }
            if (TextUtils.isEmpty(averagePlaytimeInput) && averagePlaytimeInput == ""){
                formAveragePlaytimeInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(medianPlaytimeInput) && medianPlaytimeInput == ""){
                formMedianPlaytimeInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(priceInput) && priceInput == ""){
                formPriceInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (TextUtils.isEmpty(releaseDateInput) && releaseDateInput == ""){
                formReleaseDateInput.error = "Please fill out this filed."
                checkEmpty = false
            }
            if (genresList == arrayListOf("+ Genres")) {
                formGenresText.error = "Please select genres"
                checkEmpty = false
            } else {
                formGenresText.error = null
            }
            if (categoriesList == arrayListOf("+ Categories")) {
                formCategoriesText.error = "Please select categories"
                checkEmpty = false
            }  else {
                formCategoriesText.error = null
            }
            if (tagsList == arrayListOf("+ Tags")) {
                formGameTagsText.error = "Please select game tags"
                checkEmpty = false
            } else {
                formGameTagsText.error = null
            }
            if (!platformsMacInput && !platformsLinuxInput && !platformsWindowInput)  {
                formPlatformsText.error = "Please select genres"
                checkEmpty = false
            } else {
                formPlatformsText.error = null
            }
        }

        return checkEmpty
    }

}