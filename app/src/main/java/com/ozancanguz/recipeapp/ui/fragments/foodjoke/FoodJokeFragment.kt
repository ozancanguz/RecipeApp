package com.ozancanguz.recipeapp.ui.fragments.foodjoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.databinding.FragmentFoodJokeBinding
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding


class FoodJokeFragment : Fragment() {
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }


}