package com.example.recipeapp.Api

import com.example.recipeapp.MVVM.Model.AdvancedRecipeInfo
import com.example.recipeapp.MVVM.Model.Nutrient
import com.example.recipeapp.MVVM.Model.NutritionInfo
import com.example.recipeapp.MVVM.Model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("recipes/{id}/information")
    suspend fun getRecipe(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY
    ):
            Response<Recipe>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getAdvancedRecipeInfo(
        @Path("id") id: Int ,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY
    ):
            Response<List<AdvancedRecipeInfo>>

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getNutritionInfo(
        @Path("id") id: Int ,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY
    ) :
            Response<NutritionInfo>
}