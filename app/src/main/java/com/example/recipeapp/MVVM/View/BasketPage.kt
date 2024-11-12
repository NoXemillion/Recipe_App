package com.example.recipeapp.MVVM.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.MVVM.ViewModel.RecipeViewModel
import com.example.recipeapp.R
import com.example.recipeapp.ui.theme.LightRed
import com.example.recipeapp.ui.theme.WhiteGrey
import com.example.recipeapp.ui.theme.YellowOrange

@Composable
fun BasketPage(viewModel : RecipeViewModel , navController : NavController) {


    Column(
        modifier = Modifier.fillMaxSize().padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "YOUR BASKET",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(
                    Font(R.font.roboto_medium)
                ),
                fontWeight = FontWeight.Bold
            )
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(viewModel.basketList) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(WhiteGrey)
                        .zIndex(1f)
                        .clickable {
                            viewModel.itemInfo.value = item

                            try {
                                navController.navigate("advancedInfo")
                            } catch (e: Exception) {
                                Log.d("TAG", "${e.message}")
                            }

                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))) {
                        Log.d("TAG", "Recipe title: ${item.title}")


                        Image(
                            painter = rememberAsyncImagePainter(item.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
                                .width(110.dp).height(80.dp).clip(RoundedCornerShape(9.dp))
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.title ?: "No title available",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.width(180.dp)
                                )
                                Box(modifier = Modifier.pointerInput(Unit) {}) {
                                    IconButton(onClick = {
                                        viewModel.selectedIconId.remove(item.id)
                                        viewModel.basketList.remove(item)
                                        Log.d("TAG", "Icon was pressed")
                                    }) {
                                        Icon(
                                            painterResource(
                                                if (viewModel.selectedIconId.contains(item.id)) {
                                                    R.drawable.favourite_choosed
                                                } else {
                                                    R.drawable.favourite_unchoosed
                                                }
                                            ),
                                            contentDescription = "favourite_unchoosed",
                                            tint = LightRed,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                }

                            }
                            Text(
                                text = "Will be ready in : ${item.readyInMinutes} minutes"
                                    ?: "No title available",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.alpha(0.5f)
                            )
                            Row(modifier = Modifier.fillMaxWidth()) {
                                var rating = item.rating
                                for (i in 1..rating) {
                                    Icon(
                                        painter = painterResource(R.drawable.star),
                                        contentDescription = "rating",
                                        modifier = Modifier.size(24.dp),
                                        tint = YellowOrange
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }


    }

}