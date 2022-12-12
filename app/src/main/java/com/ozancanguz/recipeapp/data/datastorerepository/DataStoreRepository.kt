package com.ozancanguz.recipeapp.data.datastorerepository

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.PREFERENCES_DIET_TYPE
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.ozancanguz.recipeapp.utils.constants.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataStoreRepository@Inject constructor(@ApplicationContext private val context:Context) {

    private object PreferenceKeys{
        val selectedMealType= stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId= intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType= stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId= intPreferencesKey(PREFERENCES_DIET_TYPE_ID)

    }


}