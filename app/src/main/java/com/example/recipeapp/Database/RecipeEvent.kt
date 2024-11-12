package com.example.recipeapp.Database

import com.example.recipeapp.MVVM.Model.Recipe

sealed interface RecipeEvent {

    data class SaveRecipe(val recipe : Recipe) : RecipeEvent
    data class DeleteRecipe(val recipe : Recipe) : RecipeEvent
}