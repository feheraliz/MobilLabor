package com.example.mobillabor.ui.categoryList

import androidx.lifecycle.*
import com.example.mobillabor.model.CategoryList
import com.example.mobillabor.repository.DataState
import com.example.mobillabor.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    private val _dataState : MutableLiveData<DataState<CategoryList>> = MutableLiveData()

    val dataState : LiveData<DataState<CategoryList>>
        get() = _dataState

    fun start() {
        viewModelScope.launch {
            recipeRepository.getCategoriesFromApi().onEach { dataState -> _dataState.value = dataState }
                .launchIn(viewModelScope)
        }
    }

}