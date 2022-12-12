package com.ozancanguz.recipeapp.data.models.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ozancanguz.recipeapp.data.models.Result
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.FAVORITERECIPES_TABLE

@Entity(tableName = FAVORITERECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result:Result

)
