package com.ozancanguz.recipeapp.data.remote

import com.ozancanguz.recipeapp.data.models.FoodRecipe
import com.ozancanguz.recipeapp.data.network.FoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor(private val foodRecipeApi: FoodRecipeApi) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }

}