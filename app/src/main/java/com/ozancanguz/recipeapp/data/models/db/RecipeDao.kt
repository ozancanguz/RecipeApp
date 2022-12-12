package com.ozancanguz.recipeapp.data.models.db

import androidx.room.*
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.data.models.db.entities.RecipesEntity
import com.ozancanguz.recipeapp.ui.fragments.favorite.FavoriteRecipes
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    // insert recipe data to dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(recipesEntity: RecipesEntity)

    // insert favorite recipes to dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoriteEntity)


    //get all recipes ref
   @Query("select * from recipes_table order by id ASC")
   fun readRecipes(): Flow<List<RecipesEntity>>


   // get all favorite recipes
    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>

    // delete single favorite recipe
    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    // delete all fav recipe
    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()








}