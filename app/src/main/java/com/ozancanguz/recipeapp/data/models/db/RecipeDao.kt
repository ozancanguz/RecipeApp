package com.ozancanguz.recipeapp.data.models.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozancanguz.recipeapp.data.models.db.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    // insert data to dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(recipesEntity: RecipesEntity)

    //get all data ref
   @Query("select * from recipes_table order by id ASC")
   fun readRecipes(): Flow<List<RecipesEntity>>





}