package com.ozancanguz.recipeapp.data.local

import com.ozancanguz.recipeapp.data.models.db.RecipeDao
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
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


}