package com.ozancanguz.recipeapp.utils.constants

class Constants {

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "97dd239ca98e45d38e09b1982f6552c6"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"



        // Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "10"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        // db
        const val RECIPES_TABLE="recipes_table"
        const val DATABASE_NAME="recipes_database"

    }

}