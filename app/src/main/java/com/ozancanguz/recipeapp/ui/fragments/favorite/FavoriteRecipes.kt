package com.ozancanguz.recipeapp.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.databinding.FragmentFavoriteRecipesBinding
import com.ozancanguz.recipeapp.databinding.FragmentFoodJokeBinding
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding


class FavoriteRecipes : Fragment() {
    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }


}