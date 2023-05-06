package com.example.mobillabor.di

import android.content.Context
import androidx.room.Room
import com.example.mobillabor.data.RecipeDao
import com.example.mobillabor.data.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DatabaseModule {

    @Singleton
    @Provides
    fun provideRecipeDb(@ApplicationContext context: Context) : RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDAO(recipeDatabase: RecipeDatabase) : RecipeDao {
        return recipeDatabase.RecipeDao()
    }
}