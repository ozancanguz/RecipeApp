package com.ozancanguz.recipeapp.data.models.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozancanguz.recipeapp.data.models.FoodJoke
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.FOODJOKETABLE

//1 for offline caching
@Entity(tableName = FOODJOKETABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}