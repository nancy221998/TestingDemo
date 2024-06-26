package com.example.demoapp.activity.pages

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.example.demoapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun rememberAsyncImagePainterWithCache(url: String, imageCache: ImageCache): Painter {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val placeholderBitmap = loadScaledDownBitmap(context, R.drawable.image, 100, 100)

    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                bitmap = imageCache.getBitmapFromCache(url.hashCode().toString())
                if (bitmap == null) {
                    val connection = URL(url).openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    val tempBitmap = BitmapFactory.decodeStream(input)
                    imageCache.addBitmapToCache(url.hashCode().toString(), tempBitmap)
                    bitmap = tempBitmap
                }
            } catch (e: Exception) {
                bitmap = placeholderBitmap
            }
        }
    }

    return remember(bitmap) {
     //   BitmapPainter(bitmap?.asImageBitmap() ?: ImageBitmap(1, 1))
        BitmapPainter(bitmap?.asImageBitmap()  ?: placeholderBitmap.asImageBitmap())
    }
}

fun loadScaledDownBitmap(context: android.content.Context, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, resId, this)
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
        inJustDecodeBounds = false
    }
    return BitmapFactory.decodeResource(context.resources, resId, options)
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}