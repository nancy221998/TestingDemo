package com.example.demoapp.activity.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.demoapp.model.MediaItem

@Composable
fun GridItem1(item: MediaItem, ImageUrl: String) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageCache = remember { ImageCache(context) }
    LoadImageHere( ImageUrl,imageCache)
}

@Composable
fun LoadImageHere( ImageUrl: String, imageCache: ImageCache){
    ImageCardWithCache(ImageUrl, imageCache)
}

@Composable
fun ImageCardWithCache(imageUrl: String, imageCache: ImageCache) {
    val painter = rememberAsyncImagePainterWithCache(imageUrl, imageCache)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,

        )
    }
}


