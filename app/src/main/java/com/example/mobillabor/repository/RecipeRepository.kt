package com.example.mobillabor.repository

import com.example.mobillabor.data.RecipeDao
import com.example.mobillabor.network.RecipeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeApi: RecipeApi
){  }