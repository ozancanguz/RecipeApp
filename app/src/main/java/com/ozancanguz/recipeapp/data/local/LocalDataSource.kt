package com.ozancanguz.recipeapp.data.local

import androidx.room.Insert
import com.ozancanguz.recipeapp.data.models.FoodJoke
import com.ozancanguz.recipeapp.data.models.db.RecipeDao
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.data.models.db.entities.FoodJokeEntity
import com.ozancanguz.recipeapp.data.models.db.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource@Inject constructor(private val recipeDao: RecipeDao){


    // insert data reference
    suspend fun insertRecipes(recipesEntity: RecipesEntity){

        recipeDao.insertData(recipesEntity)
    }

    // all data ref
    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipeDao.readRecipes()
    }
    // list favorites recipe
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>> {
        return recipeDao.readFavoriteRecipes()
    }

    // insert favorite recipes
    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity) {
        recipeDao.insertFavoriteRecipe(favoriteEntity)
    }

    // delete favorite recipes
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipeDao.deleteFavoriteRecipe(favoriteEntity)
    }

    // delete all favorite recipes
    suspend fun deleteAllFavoriteRecipes() {
        recipeDao.deleteAllFavoriteRecipes()
    }

    // list food joke
    fun readAllJokes():Flow<List<FoodJokeEntity>>{
      return  recipeDao.readAllFoodJoke()
    }

    // insert food joke
    @Insert()
   suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
       recipeDao.insertFoodJoke(foodJokeEntity)
   }


}