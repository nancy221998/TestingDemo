package com.example.demoapp.activity.pages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.collection.lruCache
import java.io.File
import java.io.FileOutputStream

class ImageCache(private val context: Context) {
    private val memoryCache = lruCache<String, Bitmap>(calculateMemoryCacheSize())

    fun getBitmapFromCache(key: String): Bitmap? {
        return memoryCache[key] ?: getBitmapFromDiskCache(key)
    }

    fun addBitmapToCache(key: String, bitmap: Bitmap) {
        if (memoryCache[key] == null) {
            memoryCache.put(key, bitmap)
        }
        addBitmapToDiskCache(key, bitmap)
    }

    private fun getBitmapFromDiskCache(key: String): Bitmap? {
        val file = File(context.cacheDir, key)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    private fun addBitmapToDiskCache(key: String, bitmap: Bitmap) {
        val file = File(context.cacheDir, key)
        if (!file.exists()) {
            try {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun calculateMemoryCacheSize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }
}

