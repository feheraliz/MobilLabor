package com.example.mobillabor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobillabor.model.RecipeDetail

@Database(entities = [RecipeDetail::class], version = 2)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun RecipeDao(): RecipeDao

}