package com.ozancanguz.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozancanguz.adapter.RecipeAdapter
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding
import com.ozancanguz.recipeapp.utils.NetworkResult
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.API_KEY
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.DEFAULT_DIET_TYPE
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.DEFAULT_MEAL_TYPE
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_API_KEY
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_DIET
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_NUMBER
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.QUERY_TYPE
import com.ozancanguz.recipeapp.utils.observeOnce
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import com.ozancanguz.recipeapp.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    // init viewmodel
    private val mainViewModel:MainViewModel by viewModels()
    private val recipeViewModel:RecipeViewModel by viewModels()

    // init adapter
    private var recipeAdapter=RecipeAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        // init rv
        initRv()

        // observe data and update ui
       // observeLiveData()
        // for observelivedata fun we read from database
        readDatabase()



    return view
    }
    private fun initRv() {
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerview.adapter=recipeAdapter
    }

    // firs we listed from database if database is not empty .
    //if database is empty we request data from api
    private fun readDatabase() {

        Log.d("RecipesFragment","readDatabase called")
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    recipeAdapter.updateData(database[0].foodRecipe)

                } else {
                    Log.d("RecipesFragment","request from api called")
                    observeLiveData() // request api data
                }
            }
        }
    }


    // observe data and update ui
    // means request api data
    private fun observeLiveData() {

        // apply queries
        mainViewModel.getRecipes(recipeViewModel.applyQueries())

        // update ui
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { recipeAdapter.updateData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache() // if there is no internet load from cache to user

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    // if there is no internet load from database firs
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipeAdapter.updateData(database[0].foodRecipe)
                }
            }
        }
    }









}