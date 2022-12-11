package com.ozancanguz.recipeapp.data.local

import com.ozancanguz.recipeapp.data.models.db.RecipeDao
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


}