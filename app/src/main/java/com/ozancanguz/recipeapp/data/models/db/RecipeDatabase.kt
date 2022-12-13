package com.ozancanguz.recipeapp.data.models.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.data.models.db.entities.FoodJokeEntity
import com.ozancanguz.recipeapp.data.models.db.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class,FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}