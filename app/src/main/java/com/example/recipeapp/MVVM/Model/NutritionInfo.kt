package com.example.recipeapp.MVVM.Model

data class NutritionInfo(
    val nutrients: List<Nutrient>
)
data class Nutrient(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)