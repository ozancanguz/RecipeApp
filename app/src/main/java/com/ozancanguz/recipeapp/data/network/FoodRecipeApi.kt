package com.ozancanguz.recipeapp.data.network

import com.ozancanguz.recipeapp.data.models.FoodJoke
import com.ozancanguz.recipeapp.data.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipeApi {


    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>


    @GET("food/jokes/random")
    suspend fun getFoodJokes(
        @Query("apiKey") apiKey:String
    ):Response<FoodJoke>




}