package com.example.mobillabor.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobillabor.model.Recipe
import com.example.mobillabor.model.RecipeList
import com.example.mobillabor.repository.DataState
import com.example.mobillabor.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    private val _dataState : MutableLiveData<DataState<RecipeList>> = MutableLiveData()

    val dataState : LiveData<DataState<RecipeList>>
        get() = _dataState

    private val _dataStateDb : MutableLiveData<DataState<List<Recipe>>> = MutableLiveData()

    val dataStateDb : LiveData<DataState<List<Recipe>>>
        get() = _dataStateDb


    fun startApi(categoryName: String) {
        viewModelScope.launch {
            recipeRepository.getRecipesFromApi(categoryName).onEach { dataState -> _dataState.value = dataState }
                .launchIn(viewModelScope)
        }
    }

    fun startDb(categoryName: String) {
        viewModelScope.launch {
            recipeRepository.getRecipes(categoryName).onEach { dataState -> _dataStateDb.value = dataState }
                .launchIn(viewModelScope)
        }
    }

    fun getValidList(recipeList1: List<Recipe>, recipeList2: List<Recipe>): List<Recipe> {
        val filteredList = mutableListOf<Recipe>()
        filteredList.addAll(recipeList1.filter { apiRecipe ->
            !recipeList2.map { it.idMeal }.contains(apiRecipe.idMeal)
        })
        filteredList.addAll(recipeList2)
        filteredList.sortBy { it.strMeal }
        return filteredList
    }
}