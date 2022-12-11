package com.ozancanguz.recipeapp.ui.fragments.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ozancanguz.recipeapp.R
import kotlinx.android.synthetic.main.fragment_recipe.view.*


class RecipeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_recipe, container, false)

        view.button.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_favoriteFragment)
        }

    return view
    }


}