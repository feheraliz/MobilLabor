package com.example.mobillabor.model

import androidx.room.PrimaryKey

data class  Category (
    @PrimaryKey(autoGenerate = true)
    var idCategory: Int = 0,
    var strCategory: String = "",
    var strCategoryThumb: String = ""

)