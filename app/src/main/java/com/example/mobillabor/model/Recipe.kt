package com.example.mobillabor.model

class Recipe (
    var id: Int,
    var name: String,
    var ingredientList: List<String>,
    var measurementList: List<String>,
    var image: String,
    var category: Category
)

