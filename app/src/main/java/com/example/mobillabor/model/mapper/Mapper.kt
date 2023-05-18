package com.example.mobillabor.model.mapper

import com.example.mobillabor.model.Recipe
import com.example.mobillabor.model.RecipeDetail

fun RecipeDetail.toRecipe() =
    Recipe(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strMealThumb = this.strMealThumb
    )