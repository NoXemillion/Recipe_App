package com.example.recipeapp.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.MVVM.Model.Recipe


@Database(entities = [Recipe::class] , version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val dao: RecipeDao
}