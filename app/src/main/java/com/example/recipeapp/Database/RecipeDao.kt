package com.example.recipeapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.recipeapp.MVVM.Model.Recipe


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe : Recipe)

    @Delete
    suspend fun delete(recipe : Recipe)

}