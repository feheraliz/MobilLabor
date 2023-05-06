package com.example.mobillabor.di

import com.example.mobillabor.data.RecipeDao
import com.example.mobillabor.network.RecipeApi
import com.example.mobillabor.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeApi: RecipeApi, recipeDao : RecipeDao) : RecipeRepository {
        return RecipeRepository(recipeApi = recipeApi, recipeDao = recipeDao)
    }
}