package com.example.mobillabor.repository

import com.example.mobillabor.data.RecipeDao
import com.example.mobillabor.model.*
import com.example.mobillabor.model.mapper.toRecipe
import com.example.mobillabor.network.RecipeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeApi: RecipeApi
){

    suspend fun getRecipes(category: String) : Flow<DataState<List<Recipe>>> = flow{
        emit(DataState.Loading)
        try {
            val recipes = recipeDao.getAllRecipes(category).map {
                it.toRecipe()
            }
            emit(DataState.Success(recipes))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getRecipesFromApi(category: String) : Flow<DataState<RecipeList>> = flow{
        emit(DataState.Loading)
        try {
            val recipes = recipeApi.getRecipes(category)
            emit(DataState.Success(recipes))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getCategoriesFromApi() : Flow<DataState<CategoryList>> = flow{
        emit(DataState.Loading)
        try {
            val categories = recipeApi.getCategories()
            emit(DataState.Success(categories))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getRecipeDetails(id: Int): Flow<DataState<RecipeDetail>> = flow{
        emit(DataState.Loading)
        try {
            val recipe = recipeDao.getRecipe(id)
            emit(DataState.Success(recipe))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getRecipeDetailsFromApi(id: Int): Flow<DataState<RecipeDetailList>> = flow{
        emit(DataState.Loading)
        try {
            val recipe = recipeApi.getRecipeDetails(id)
            emit(DataState.Success(recipe))
        }
        catch(e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // DOES NOT EXIST ON API
    suspend fun addRecipe( recipe: RecipeDetail ) {
        recipeDao.addRecipe(recipe)
    }

    // DOES NOT EXIST ON API
    suspend fun updateRecipe(recipe: RecipeDetail) {
        recipeDao.update(recipe)
    }

    // DOES NOT EXIST ON API
    suspend fun deleteRecipe(recipe: RecipeDetail) {
        recipeDao.deleteRecipe(recipe)
    }
}