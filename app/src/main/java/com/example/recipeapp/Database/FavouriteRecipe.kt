package com.example.recipeapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favouriteRecipes")
data class FavouriteRecipe(
    val id : Int = 0,
    val title : String ,
    val image : String ,
    val imageType : String,
    val readyInMinutes : Int ,
    val veryPopular : Boolean ,
    val rating : Int
)