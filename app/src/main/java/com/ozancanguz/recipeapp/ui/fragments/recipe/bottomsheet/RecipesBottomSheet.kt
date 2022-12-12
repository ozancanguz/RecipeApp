package com.ozancanguz.recipeapp.ui.fragments.recipe.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozancanguz.recipeapp.R

import com.ozancanguz.recipeapp.databinding.RecipesBottomSheetBinding


class RecipesBottomSheet : BottomSheetDialogFragment() {
    private var _binding: RecipesBottomSheetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root







        return view
    }


}