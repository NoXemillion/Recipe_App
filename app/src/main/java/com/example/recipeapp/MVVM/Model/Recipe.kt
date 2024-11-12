package com.example.recipeapp.MVVM.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class RecipeResponse(
    val results: List<Recipe>
)

@Entity()
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String ,
    val image : String ,
    val imageType : String,
    val readyInMinutes : Int ,
    val veryPopular : Boolean ,
    val rating : Int
)