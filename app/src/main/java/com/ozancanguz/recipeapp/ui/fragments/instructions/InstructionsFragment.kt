package com.ozancanguz.recipeapp.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.data.models.Result
import com.ozancanguz.recipeapp.databinding.FragmentInstructionsBinding


class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        binding.instructionsWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)


        return view
    }

}