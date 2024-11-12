package com.example.recipeapp.MVVM.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.MVVM.Model.Recipe
import com.example.recipeapp.MVVM.ViewModel.RecipeViewModel
import com.example.recipeapp.R
import com.example.recipeapp.ui.theme.YellowOrange


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeInfo(viewModel : RecipeViewModel , navController: NavController){

    val recipeInformation = viewModel.itemInfo

    LaunchedEffect(recipeInformation.value) {
        recipeInformation.value?.let { viewModel.takeAdvancedInfo(it.id) }
        recipeInformation.value?.let { viewModel.takeNutritionInfo(it.id)}
    }

    val advancedInfo = viewModel.advancedInfo
    val nutritionInfo = viewModel.nutritionInfo



    Log.d("TAG2", "Advanced Info: $advancedInfo")
    Scaffold (){
        paddingValues ->

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)
            .padding(start = 20.dp , top =70.dp)){
            recipeInformation.value?.let{ recipe ->

                Row{
                    Text(text = recipe.title , style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight.Bold),
                        modifier = Modifier.width(250.dp))


//                    recipeInformation.value?.let { item ->
//                        Image(
//                            painter = rememberAsyncImagePainter(item.image),
//                            contentDescription = "image",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(200.dp).padding(top = 50.dp , end = 50.dp).clip(
//                                RoundedCornerShape(15.dp)
//                                ),
//                            )
//                        }

                }

                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    for (i in 1..recipe.rating) {
                        Icon(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "rating",
                            modifier = Modifier.size(34.dp),
                            tint = YellowOrange
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp) ,
                        modifier = Modifier.fillMaxWidth().heightIn(max = 500.dp).verticalScroll(rememberScrollState())){
                        Column {
                            nutritionInfo.value?.nutrients?.forEach { nutritiet ->
                                if (nutritiet.name.equals("Calories")) {
                                    Text(
                                        text = nutritiet.name,
                                        style = TextStyle(
                                            fontSize = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.width(60.dp).alpha(0.5f)
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row {
                                        Text(
                                            text = nutritiet.amount.toString(),
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.width(60.dp)
                                        )
                                        Text(
                                            text = "Calories",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.width(80.dp)
                                        )

                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Time",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.width(60.dp).alpha(0.5f)
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Row {
                                recipeInformation.value?.let { item ->
                                    Text(
                                        text = item.readyInMinutes.toString(),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.width(25.dp)
                                    )
                                }
                                Text(
                                    text = "mins",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.width(80.dp)
                                )

                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Instruction",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.wrapContentWidth().alpha(0.5f)
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Column(modifier = Modifier
                                .fillMaxSize()) {
                                advancedInfo.forEach { mainlist ->
                                    mainlist.forEach { list ->
                                        list.steps.forEach { step ->
                                            Text(
                                                text = step.step,
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.wrapContentWidth()
                                            )

                                        }

                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = "Ingredients",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.wrapContentWidth().alpha(0.5f)
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Row {
                                advancedInfo.forEach { mainlist ->
                                    mainlist.forEach { steps ->
                                        steps.steps.forEach { ingridients ->
                                            ingridients.ingredients.withIndex()
                                                .forEach { (index, item) ->
                                                    if (index < ingridients.ingredients.size - 1) {
                                                        Text(
                                                            text = "${item.name}, ",
                                                            style = TextStyle(
                                                                fontSize = 16.sp,
                                                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                                                fontWeight = FontWeight.Bold
                                                            ),
                                                            modifier = Modifier.wrapContentWidth()
                                                        )
                                                    } else {
                                                        Text(
                                                            text = item.name,
                                                            style = TextStyle(
                                                                fontSize = 16.sp,
                                                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                                                fontWeight = FontWeight.Bold
                                                            ),
                                                            modifier = Modifier.wrapContentWidth()
                                                        )
                                                    }

                                                }

                                        }
                                    }
                                }
                            }
//                            Box(modifier = Modifier.fillMaxWidth() ,
//                                contentAlignment = Alignment.Center){
//                                Button(onClick = {
//                                    navController.navigate("main")
//                                }){
//                                    Text(text = "Back")
//                                }
//                            }



                        }
                        }

                    }

                }

            }
        }

}