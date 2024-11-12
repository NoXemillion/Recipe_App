package com.example.recipeapp.MVVM.View



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.recipeapp.Database.RecipeEvent
import com.example.recipeapp.MVVM.Model.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.MVVM.ViewModel.RecipeViewModel
import com.example.recipeapp.ui.theme.LightRed
import com.example.recipeapp.ui.theme.PinkRed
import com.example.recipeapp.ui.theme.WhiteGrey
import com.example.recipeapp.ui.theme.YellowOrange





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(viewModel: RecipeViewModel, navController: NavController ,
                     onEvent : (RecipeEvent) -> Unit) {


    var searchText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        viewModel.fillList()
    }

    var filteredItems = viewModel.receptList.filter {
        it.title.contains(searchText, ignoreCase = true)
    }

    var isVisible by remember {
        mutableStateOf(true)
    }

    var titleName by remember {
        mutableStateOf("")
    }

    var exactCard by remember {
        mutableStateOf(false)
    }

    var favouriteIcon by remember {
        mutableStateOf(R.drawable.favourite_unchoosed)
    }



    Scaffold(
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(top = 60.dp, start = 12.dp, end = 16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                        painter = painterResource(R.drawable.tatti), contentDescription = "basket",
                        modifier = Modifier.height(35.dp).width(100.dp).clip(RoundedCornerShape(4.dp))
                    )

                IconButton(onClick = {
                    navController.navigate("basketPage")
                }) {
                    Icon(
                        painter = painterResource(R.drawable.mall), contentDescription = "basket",
                        tint = PinkRed
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "What would you like to cook today?", style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = { Text("Search for recipes") },
                        leadingIcon = {
                            IconButton (onClick = {

                            }){
                                Icon(
                                    painter = painterResource(R.drawable.search), contentDescription = "basket",
                                    tint = PinkRed
                                )
                            }
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = WhiteGrey,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }


                if (searchText.isNotBlank()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().then(
                            if (isVisible) Modifier else Modifier.size(0.dp)
                        ).zIndex(2f)
                    )
                    {
                        items(filteredItems) {
                            item ->
                            Text(
                                text = item.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp).clickable {
                                        searchText = item.title
                                        isVisible = false
                                        exactCard = true
                                    }
                            )
                            titleName = item.title
                        }
                    }
                } else {
                    isVisible = true
                    exactCard = false
                }


                LazyColumn(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(15.dp)),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(viewModel.receptList) { item ->
                        if(exactCard){
//                            val isSelected = viewModel.selectedIcon.value == item.id
                            if(titleName.equals(item.title)){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(WhiteGrey)
                                        .zIndex(1f)
                                        .clickable {
                                            viewModel.itemInfo.value = item

                                            try{
                                                navController.navigate("advancedInfo")
                                            }
                                            catch(e : Exception){
                                                Log.d("TAG" , "${e.message}")
                                            }

                                        }
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))) {
                                        Log.d("TAG", "Recipe title: ${item.title}")


                                        Image(
                                            painter = rememberAsyncImagePainter(item.image),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.padding(start = 20.dp, end = 20.dp , top = 10.dp)
                                                .width(110.dp).height(80.dp).clip(RoundedCornerShape(9.dp))
                                        )

                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween){
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
                                                        viewModel.selectedIconId.add(item.id)
                                                        viewModel.basketList.add(item)
                                                        onEvent(RecipeEvent.SaveRecipe(item))
                                                        Log.d("TAG" , "Icon was pressed")
                                                    }) {
                                                        Icon(
                                                            painterResource(
                                                                if(viewModel.selectedIconId.contains(item.id)){
                                                                    R.drawable.favourite_choosed
                                                                }else{
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
                        }else{


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(WhiteGrey)
                                    .zIndex(1f)
                                    .clickable {
                                        viewModel.itemInfo.value = item
                                        try{
                                            navController.navigate("advancedInfo")
                                        }
                                        catch(e : Exception){
                                            Log.d("TAG" , "${e.message}")
                                        }

                                    }
                            ) {
                                Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp))) {
                                    Log.d("TAG", "Recipe title: ${item.title}")


                                    Image(
                                        painter = rememberAsyncImagePainter(item.image),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp , top = 10.dp)
                                            .width(110.dp).height(80.dp).clip(RoundedCornerShape(9.dp))
                                    )

                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween){
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
                                                    viewModel.selectedIconId.add(item.id)
                                                    viewModel.basketList.add(item)
                                                    onEvent(RecipeEvent.SaveRecipe(item))
                                                }) {
                                                    Icon(
                                                        painterResource(
                                                            if(viewModel.selectedIconId.contains(item.id)){
                                                                R.drawable.favourite_choosed
                                                            }
                                                            else{
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
        }

    }
}
