package com.example.recipeapp.MVVM.Model

data class AdvancedRecipeInfo(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>
)

data class Equipment(
    val id: Int,
    val image: String,
    val name: String,
    val temperature: Temperature? = null
)

data class Temperature(
    val number: Double,
    val unit: String
)

data class Ingredient(
    val id: Int,
    val image: String,
    val name: String
)