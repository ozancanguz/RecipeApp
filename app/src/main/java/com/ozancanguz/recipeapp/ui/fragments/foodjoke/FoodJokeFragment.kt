package com.ozancanguz.recipeapp.ui.fragments.foodjoke

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
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.databinding.FragmentFoodJokeBinding
import com.ozancanguz.recipeapp.databinding.FragmentRecipesBinding
import com.ozancanguz.recipeapp.utils.NetworkResult
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.API_KEY
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke"


    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        val view = binding.root



        observeLiveData()


        return view
    }

    private fun observeLiveData() {
        mainViewModel.getAllFoodJoke(API_KEY)
        mainViewModel.foodjokeresponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                    if (response.data != null) {
                        foodJoke = response.data.text
                    }
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
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }
    }
    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner, Observer{database->
                if(!database.isNullOrEmpty()){
                    binding.foodJokeTextView.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

