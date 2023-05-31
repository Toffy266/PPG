package com.example.predictpopularityofgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.predictpopularityofgame.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private lateinit var fragmentOnBoardingBinding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentOnBoardingBinding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return fragmentOnBoardingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentOnBoardingBinding.onBoardingButton.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToCheckHavingRatingFragment())
        }
    }
}