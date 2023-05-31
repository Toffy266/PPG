package com.example.predictpopularityofgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.predictpopularityofgame.adapter.FeatureAdapter
import com.example.predictpopularityofgame.databinding.FragmentPredictResultBinding
import com.google.android.flexbox.*

class ResultPredictFragment : Fragment() {
    private lateinit var fragmentPredictResultBinding : FragmentPredictResultBinding
    private val args: ResultPredictFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fragmentPredictResultBinding = FragmentPredictResultBinding.inflate(layoutInflater)
        return fragmentPredictResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val negativeLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexWrap = FlexWrap.WRAP
        }

        val positiveLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexWrap = FlexWrap.WRAP
        }

        val estimatedSales = args.predictResult.estimated_sales_class
        val nameGame = args.gameName
        val negativeFeat = args.predictResult.negative
        val positiveFeat = args.predictResult.positive

        with(fragmentPredictResultBinding) {

            predictResultSalesItem.apply {
                gameName.text = nameGame
                predictResultSalesText.text = mapSales(estimatedSales)
                ratedImg.setImageResource(mapRated(estimatedSales))
            }

            predictResultFeatureItem.apply {
                negativeFeaturesRecycleView.layoutManager = negativeLayoutManager
                negativeFeaturesRecycleView.adapter = FeatureAdapter(negativeFeat)

                positiveFeaturesRecycleView.layoutManager = positiveLayoutManager
                positiveFeaturesRecycleView.adapter = FeatureAdapter(positiveFeat)
            }

            predictAgainButton.setOnClickListener {
                findNavController().navigate(ResultPredictFragmentDirections.actionResultPredictFragmentToCheckHavingRatingFragment())
            }
        }
    }

    private fun mapSales(estimatedSales: Int): String {
        return when (estimatedSales) {
            0 -> "0 - 20K"
            1 -> "20K - 50K"
            2 -> "50K -100K"
            3 -> "100K - 200K"
            4 -> "200K - 500K"
            5 -> "500K - 1,000K"
            6 -> "1,000K - 2,000K"
            7 -> "2,000K - 5,000K"
            8 -> "5,000K - 10,000K"
            else -> {
                "10,000K - 200,000K"
            }
        }
    }

    private fun mapRated(estimatedSales: Int): Int {
        return if (estimatedSales in 0..2) {
            R.drawable.average
        } else if (estimatedSales in 3..5) {
            R.drawable.good
        } else {
            R.drawable.excellent
        }
    }

}