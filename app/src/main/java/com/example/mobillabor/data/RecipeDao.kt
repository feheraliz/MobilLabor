package com.example.mobillabor.data

import androidx.room.*
import com.example.mobillabor.model.RecipeDetail

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeDetail): Long

    @Delete
    suspend fun deleteRecipe(recipe: RecipeDetail)

    @Query("SELECT * FROM recipes WHERE strCategory = :category")
    suspend fun getAllRecipes(category: String): List<RecipeDetail>

    @Query("SELECT * FROM recipes WHERE idMeal = :id")
    suspend fun getRecipe(id: Int): RecipeDetail

    @Update
    suspend fun update(recipe: RecipeDetail): Int
}