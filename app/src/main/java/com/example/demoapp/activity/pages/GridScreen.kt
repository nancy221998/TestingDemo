package com.example.demoapp.activity.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.activity.MainActivity
import com.example.demoapp.model.Media
import com.example.demoapp.other.DataLoadState
import com.example.demoapp.other.isInternetAvailable
import com.example.demoapp.viewmodel.HomeViewModel

@Composable
fun GridScreen(context: MainActivity) {
    val homeViewModel: HomeViewModel = hiltViewModel()
     val postMessage by homeViewModel.dataLoadStateFlow.collectAsState(DataLoadState.Start)
    Log.e("TAG", "GridScreen: $postMessage", )
    when(postMessage){
      is DataLoadState.Failed -> ShowSomethingWrongDialog()
        DataLoadState.Loading ->    ShowLoadingStage(context)
        DataLoadState.Start -> print("start called")
        is DataLoadState.Success -> {
            ShowListItem((postMessage as DataLoadState.Success).data as Media,context)
        }
     }
    }

@Composable
fun ShowListItem(media: Media, context: MainActivity) {
    if (isInternetAvailable(context)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
           items(media) { item ->
                val ImageUrl =
                    item.thumbnail.domain + "/" + item.thumbnail.basePath + "/0/" + item.thumbnail.key
                GridItem1(item, ImageUrl)
            }
        }
    }else{
        ShowNoInterNetConnectionDialog()
    }
}

@Composable
fun ShowLoadingStage(context: MainActivity) {
    if (!isInternetAvailable(context)) { ShowNoInterNetConnectionDialog()}
    else {
        CustomBox() {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Loading",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    }
                }
            }

@Composable
fun ShowSomethingWrongDialog(){
    CustomBox() {
         Text(
            text = "Something Went Wrong !",
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ShowNoInterNetConnectionDialog(){
    val homeViewModel: HomeViewModel = hiltViewModel()
    Surface(color = Color.White,
       shape = RoundedCornerShape(10.dp),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .clickable { homeViewModel.fetchData() }

    ) {
        Box(contentAlignment = Alignment.Center){
            Text(
                text = "No Internet Connection , Try Again !",
                fontSize = 10.sp,
                color = Color.Black,
                 textAlign = TextAlign.Center
             )
        }
    }

}

