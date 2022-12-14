package com.ozancanguz.recipeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.data.models.FoodRecipe
import com.ozancanguz.recipeapp.data.models.Result
import com.ozancanguz.recipeapp.ui.fragments.recipe.RecipesFragmentDirections
import com.ozancanguz.recipeapp.utils.util.Companion.loadImage
import kotlinx.android.synthetic.main.recipes_row_layout.view.*

class RecipeAdapter:RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    var recipeList= emptyList<Result>()

    fun updateData(newData: FoodRecipe){
        this.recipeList=newData.results
        notifyDataSetChanged()
    }

    class RecipeViewHolder(view: View):RecyclerView.ViewHolder(view) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recipes_row_layout,parent,false)
        return RecipeViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        var currentItem=recipeList[position]

        holder.itemView.title_textView.text=currentItem.title
        holder.itemView.description_textView.text=currentItem.summary

        //glide ile sonradan ekledik
        holder.itemView.recipe_imageView.loadImage(currentItem.image)
        holder.itemView.heart_textView.text=currentItem.aggregateLikes.toString()
        holder.itemView.clock_textView.text=currentItem.readyInMinutes.toString()
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context
            ,R.color.customColor))
        holder.itemView.leaf_imageView
            .setBackgroundColor(ContextCompat.getColor(holder.itemView.context
            ,R.color.customColor))


        // result arg
        holder.itemView.setOnClickListener {
            val action=RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


}