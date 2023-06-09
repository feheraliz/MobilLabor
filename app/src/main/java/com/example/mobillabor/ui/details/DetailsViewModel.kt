package com.example.mobillabor.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobillabor.model.RecipeDetail
import com.example.mobillabor.model.RecipeDetailList
import com.example.mobillabor.repository.DataState
import com.example.mobillabor.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _dataState : MutableLiveData<DataState<RecipeDetailList>> = MutableLiveData()

    val dataState : LiveData<DataState<RecipeDetailList>>
        get() = _dataState

    private val _dataStateDb : MutableLiveData<DataState<RecipeDetail>> = MutableLiveData()

    val dataStateDb : LiveData<DataState<RecipeDetail>>
        get() = _dataStateDb
    fun start(id: Int) {
        _id.value = id
        viewModelScope.launch {
            recipeRepository.getRecipeDetailsFromApi(_id.value!!).onEach {
                    dataState -> _dataState.value = dataState }.launchIn(viewModelScope)
        }
    }

    fun startDb(id: Int) {
        _id.value = id
        viewModelScope.launch {
            recipeRepository.getRecipeDetails(_id.value!!).onEach {
                    dataState -> _dataStateDb.value = dataState }.launchIn(viewModelScope)
        }
    }

    fun deleteRecipe(recipe: RecipeDetail) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }
}