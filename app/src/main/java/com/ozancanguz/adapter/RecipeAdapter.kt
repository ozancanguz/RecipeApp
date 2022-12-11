package com.ozancanguz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.data.models.FoodRecipe
import com.ozancanguz.recipeapp.data.models.Result
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



    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


}