package com.ozancanguz.recipeapp.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozancanguz.recipeapp.adapter.IngredientsAdapter
import com.ozancanguz.recipeapp.data.models.Result
import com.ozancanguz.recipeapp.databinding.FragmentIngredientsBinding


class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private var  ingredientsAdapter=IngredientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRv(view)


        val args=arguments
        val myBundle: com.ozancanguz.recipeapp.data.models.Result? = args?.getParcelable<Result>("recipeBundle")

        myBundle?.extendedIngredients.let {
            if (it != null) {
                ingredientsAdapter.setData(it)
            }
        }

        return view
    }

    fun setupRv(view:View){
        binding.ingredientsRecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.ingredientsRecyclerview.adapter=ingredientsAdapter
    }

}


