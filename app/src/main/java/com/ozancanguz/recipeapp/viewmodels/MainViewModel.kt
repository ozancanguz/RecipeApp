package com.ozancanguz.recipeapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.ozancanguz.recipeapp.data.Repository
import com.ozancanguz.recipeapp.data.models.FoodJoke
import com.ozancanguz.recipeapp.data.models.FoodRecipe
import com.ozancanguz.recipeapp.data.models.db.RecipeDao
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.data.models.db.entities.FoodJokeEntity
import com.ozancanguz.recipeapp.data.models.db.entities.RecipesEntity
import com.ozancanguz.recipeapp.utils.NetworkResult
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,

    application: Application):AndroidViewModel(application){

 // ----------------------------FOR ROOM --------------------------------

    // get all data for room
    val readRecipes:LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()
    val readFavoriteRecipes:LiveData<List<FavoriteEntity>> = repository.local.readFavoriteRecipes().asLiveData()
     val readFoodJoke:LiveData<List<FoodJokeEntity>> = repository.local.readAllJokes().asLiveData()


    // insert recipe for viewmodel ref

    private fun insertRecipe(recipesEntity: RecipesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }
    fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoriteEntity)
        }
    }

     fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoriteEntity)
        }
    }

    fun deleteAllFavoriteRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRecipes()
        }
    }


    // insert food joke

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        viewModelScope.launch {
            repository.local.insertFoodJoke(foodJokeEntity)
        }
    }






    //       -----------------FOR RETROFİT----------------
    // recipe response
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    // search recipe response
    var searchedRecipesResponse:MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()


    // get all food joke
    var foodjokeresponse:MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    // get recipes in background thread
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
          getRecipesSafeCall(queries)
    }

    // search recipes in background thread
    fun searchRecipes(searchQuery:Map<String,String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    // get all foodjoke
    fun getAllFoodJoke(apiKey:String)=viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodjokeresponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodjokeresponse.value=handleFoodJokeResponse(response)

                // for offline cache part 1
                val foodRecipe = foodjokeresponse.value!!.data
                if(foodRecipe != null) {

                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }

    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
                // for offline cache part 1
                val foodRecipe = recipesResponse.value!!.data
                if(foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }
    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                recipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }




    // offline cache recipe part 2
    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipe(recipesEntity)


    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }


            response.isSuccessful -> {
                val foodJoke = response.body()
                return NetworkResult.Success(foodJoke!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    // 1
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }





    }





