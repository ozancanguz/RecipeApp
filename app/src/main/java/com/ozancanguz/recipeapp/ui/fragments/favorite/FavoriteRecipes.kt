package com.ozancanguz.recipeapp.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.adapter.FavoritesRecipesAdapter
import com.ozancanguz.recipeapp.databinding.FragmentFavoriteRecipesBinding
import com.ozancanguz.recipeapp.databinding.FragmentFoodJokeBinding
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipes : Fragment() {
    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    private var favoritesRecipesAdapter=FavoritesRecipesAdapter()

    private val mainViewModel:MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

         // set up rv
        setUpRv()

        // observe and update ui
       observeData()

        return view
    }

    private fun setUpRv() {
        binding.favoriteRecipesRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.favoriteRecipesRecyclerView.adapter=favoritesRecipesAdapter
    }

    fun observeData(){
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, Observer { favList ->

            favoritesRecipesAdapter.updateFavData(favList)

        })


    }




}