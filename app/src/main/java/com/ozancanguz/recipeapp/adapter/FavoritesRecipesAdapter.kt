package com.ozancanguz.recipeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.recipeapp.R

import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.utils.util.Companion.loadImage
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*


class FavoritesRecipesAdapter:RecyclerView.Adapter<FavoritesRecipesAdapter.FavViewHolder>() {

    var favlist= emptyList<FavoriteEntity>()

    fun updateFavData(newData:List<FavoriteEntity>){
        this.favlist=newData
        notifyDataSetChanged()
    }


    class FavViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.favorite_recipes_row_layout,parent,false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
       var currentFav=favlist[position]
        holder.itemView.favorite_title_textView.text=currentFav.result.title
        holder.itemView.favorite_description_textView.text=currentFav.result.summary

        holder.itemView.favorite_recipe_imageView.loadImage(currentFav.result.image)
        holder.itemView.favorite_heart_textView.text=currentFav.result.aggregateLikes.toString()
       holder.itemView.favorite_clock_textView.text=currentFav.result.readyInMinutes.toString()
        holder.itemView.favorite_leaf_imageView
            .setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context
                ,R.color.customColor))



    }

    override fun getItemCount(): Int {
       return favlist.size
    }
}