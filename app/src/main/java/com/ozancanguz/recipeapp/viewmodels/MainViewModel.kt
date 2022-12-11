package com.ozancanguz.recipeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ozancanguz.recipeapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: Repository,application: Application):AndroidViewModel(application) {






}