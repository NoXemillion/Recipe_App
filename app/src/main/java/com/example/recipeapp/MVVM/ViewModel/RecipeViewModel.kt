package com.example.recipeapp.MVVM.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.MVVM.Model.AdvancedRecipeInfo
import com.example.recipeapp.Api.RetrofitInstance
import com.example.recipeapp.Database.FavouriteRecipe
import com.example.recipeapp.Database.RecipeDao
import com.example.recipeapp.Database.RecipeEvent
import com.example.recipeapp.MVVM.Model.Nutrient
import com.example.recipeapp.MVVM.Model.NutritionInfo
import com.example.recipeapp.MVVM.Model.Recipe
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class RecipeViewModel(dao : RecipeDao) : ViewModel(){

    var error = mutableStateOf<String?>(null)
    val receptList = mutableStateListOf<Recipe>()
    var advancedInfo = mutableStateListOf<List<AdvancedRecipeInfo>>()
    var itemInfo = mutableStateOf<Recipe?>(null)
    var nutritionInfo = mutableStateOf<NutritionInfo?>(null)
    var basketList = mutableListOf<Recipe>()
    var selectedIconId = mutableStateListOf<Int>()

    var title = ""
    var image = ""
    var imageType = ""
    var readyInMinutes = 0
    var veryPopular = true
    var rating = 0

    var dao = dao

    fun onEvent(event : RecipeEvent){
        when(event) {
            is RecipeEvent.SaveRecipe -> {
                val item = event.recipe
                title = item.title
                image = item.image
                imageType = item.imageType
                readyInMinutes = item.readyInMinutes
                veryPopular = item.veryPopular
                rating = item.rating

                val recipe = Recipe(
                    title = title,
                    image = image ,
                    imageType = imageType ,
                    readyInMinutes = readyInMinutes,
                    veryPopular = veryPopular,
                    rating = rating
                )
                viewModelScope.launch {
                    dao.insert(recipe)
                }

            }
            is RecipeEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    dao.delete(event.recipe)
                }
            }
        }
    }

    fun fillList(){

        viewModelScope.launch {
            val defferedRecipes = (1..120).map{ id ->
                async{
                    fetchData(id)
                }
            }
            defferedRecipes.awaitAll().forEach { recipe ->
                recipe?.let{ item ->
                    receptList.add(
                        Recipe(
                            id = item.id,
                            title = item.title,
                            image = item.image,
                            imageType = item.imageType,
                            readyInMinutes = item.readyInMinutes,
                            veryPopular = item.veryPopular ,
                            rating = Random.nextInt(1,6)
                        )
                    )

                }
            }
        }

    }

    fun takeAdvancedInfo(id : Int){
        viewModelScope.launch {
            val result = fetchAdvancedData(id)
            Log.d("TAG3" , "$result")
            result?.let{
                advancedInfo.add(result)
            }

        }

    }

    fun takeNutritionInfo(id : Int){
        viewModelScope.launch {
            val result = fetchNutritionData(id)
            Log.d("TAG4" , "$result")
            result?.let{
                nutritionInfo.value = result
            }
        }
    }



    suspend fun fetchData(id : Int) : Recipe?{
        return try {
            val response: Response<Recipe> = RetrofitInstance.api.getRecipe(id)
            if (response.isSuccessful) {
                Log.d("TAG" , "${response.body()}")
                response.body()
            } else {
                Log.d("TAG", "Ошибка: код = ${response.code()}, сообщение = ${response.errorBody()?.string()}")
                error.value = "Ошибка"
                null
            }
        } catch (e: Exception) {
            error.value = "$e"
            null
        }
    }

    suspend fun fetchAdvancedData(id : Int) : List<AdvancedRecipeInfo>?{
        return try{
            val response: Response<List<AdvancedRecipeInfo>> = RetrofitInstance.api.getAdvancedRecipeInfo(id)
            if(response.isSuccessful){
                response.body()
            }
            else {
                error.value = "Ошибка"
                Log.d("TAG3" , "${error.value}")
                null
            }
        }catch(e : Exception){
            error.value = "$e"
            Log.d("TAG3" , "${error.value}")
            null
        }
    }

    suspend fun fetchNutritionData(id : Int) : NutritionInfo? {
        return try{
            val response : Response<NutritionInfo> = RetrofitInstance.api.getNutritionInfo(id)
            if(response.isSuccessful){
                response.body()
            }
            else{
                error.value = "Ошибка"
                Log.d("TAG4" , "${error.value}")
                null
            }
        }catch(e : Exception){
            error.value = "$e"
            Log.d("TAG4" , "${error.value}")
            null
        }
    }
}