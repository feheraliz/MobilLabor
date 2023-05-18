package com.example.mobillabor.network

import com.example.mobillabor.model.*
import retrofit2.http.*

interface RecipeApi {

    @GET("filter.php")
    suspend fun getRecipes(
        @Query("c") type: String = "Recipe",
    ) : RecipeList

    @GET("categories.php")
    suspend fun getCategories(
    ) : CategoryList

    @GET("lookup.php")
    suspend fun getRecipeDetails(
        @Query("i") type: Int,
    ) : RecipeDetailList

    // DOES NOT EXIST ON API
    @POST("add.php")
    suspend fun addRecipe(
        @Body recipe: RecipeDetail
    ) : Int

    // DOES NOT EXIST ON API
    @PUT("update.php")
    suspend fun updateRecipe(
        @Body recipe: RecipeDetail
    ) : Int

    // DOES NOT EXIST ON API
    @DELETE("delete.php")
    suspend fun deleteRecipe(
        @Query("d") type: Int,
    )
}