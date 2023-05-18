package com.example.mobillabor.model

import androidx.room.PrimaryKey

data class Recipe (
    @PrimaryKey(autoGenerate = true)
    var idMeal: Int = 0,
    var strMeal: String = "",
    var strMealThumb: String = ""
)

