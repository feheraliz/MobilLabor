package com.example.mobillabor.data

import androidx.room.RoomDatabase

abstract class RecipeDatabase : RoomDatabase() {

    abstract fun RecipeDao(): RecipeDao
}