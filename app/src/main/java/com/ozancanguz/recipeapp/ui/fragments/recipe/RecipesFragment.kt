package com.ozancanguz.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.adapter.RecipeAdapter
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding
import com.ozancanguz.recipeapp.utils.NetworkListener
import com.ozancanguz.recipeapp.utils.NetworkResult
import com.ozancanguz.recipeapp.utils.observeOnce
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import com.ozancanguz.recipeapp.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args:RecipesFragmentArgs by navArgs()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    // init viewmodel
    private val mainViewModel:MainViewModel by viewModels()
    private val recipeViewModel:RecipeViewModel by viewModels()

    // init adapter
    private var recipeAdapter=RecipeAdapter()

    // network listeer for internet connection
    private lateinit var networkListener: NetworkListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        // set menu
        //for menu
        setHasOptionsMenu(true)

        // init rv
        initRv()

        // observe data and update ui
       // observeLiveData()
        // for observelivedata fun we read from database
        readDatabase()

        //network listener // if there is no internet it logs false
        lifecycleScope.launch{
            networkListener= NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{status ->
                    Log.d("networkListener",status.toString())
                    recipeViewModel.showNetworkStatus()
                }

        }



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }


    return view
    }

    private fun initRv() {
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerview.adapter=recipeAdapter
    }



    // firs we listed from database if database is not empty .
    //if database is empty we request data from api
    private fun readDatabase() {


        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.back ) {
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

    // show menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_search_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)


    }
    override fun onQueryTextSubmit(query: String?): Boolean {

        if(query != null){
            searchApiData(query)
        }
        return true
    }


    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


    private fun searchApiData(searchQuery: String) {

        mainViewModel.searchRecipes(recipeViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    val foodRecipe = response.data
                    foodRecipe?.let { recipeAdapter.updateData(it) }
                }
                is NetworkResult.Error -> {

                    loadDataFromCache()
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










}