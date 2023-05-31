package com.example.predictpopularityofgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.predictpopularityofgame.databinding.FragmentCheckHavingRatingBinding
import com.example.predictpopularityofgame.util.Check

class CheckHavingRatingFragment : Fragment() {
    private lateinit var fragmentCheckHavingRatingBinding : FragmentCheckHavingRatingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fragmentCheckHavingRatingBinding = FragmentCheckHavingRatingBinding.inflate(layoutInflater)
        return fragmentCheckHavingRatingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentCheckHavingRatingBinding) {
            yesButton.setOnClickListener {
                Check.havingRating = true
                findNavController().navigate(
                    CheckHavingRatingFragmentDirections.actionCheckHavingRatingFragmentToFormFragment ()
                )
            }

            noButton.setOnClickListener {
                Check.havingRating = false
                findNavController().navigate(
                    CheckHavingRatingFragmentDirections.actionCheckHavingRatingFragmentToFormFragment ()
                )
            }
        }

    }

}