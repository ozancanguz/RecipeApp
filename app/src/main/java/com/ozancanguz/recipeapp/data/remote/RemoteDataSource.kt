package com.ozancanguz.recipeapp.data.remote

import com.ozancanguz.recipeapp.data.models.FoodJoke
import com.ozancanguz.recipeapp.data.models.FoodRecipe
import com.ozancanguz.recipeapp.data.network.FoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor(private val foodRecipeApi: FoodRecipeApi) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }


    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apikey:String):Response<FoodJoke>{
        return  foodRecipeApi.getFoodJokes(apikey)

    }

}