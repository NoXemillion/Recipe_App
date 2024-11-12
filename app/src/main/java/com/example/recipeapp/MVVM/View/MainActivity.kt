package com.example.recipeapp.MVVM.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.ui.theme.RecipeAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.recipeapp.Database.RecipeDatabase
import com.example.recipeapp.MVVM.ViewModel.RecipeViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext ,
            RecipeDatabase::class.java ,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<RecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                MainScreen(viewModel)

            }
        }
    }


}

@Composable
fun MainScreen(viewModel : RecipeViewModel){
    var navController = rememberNavController()
    NavHost(
        navController = navController ,
        startDestination = "main")
    {
        composable("main") { RecipeListScreen(viewModel , navController , onEvent = viewModel::onEvent) }
        composable("advancedInfo") { RecipeInfo(viewModel , navController) }
        composable("basketPage"){ BasketPage(viewModel , navController) }

    }
}

