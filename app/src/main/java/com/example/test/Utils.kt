package com.example.test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.collection.LruCache
import java.io.ByteArrayOutputStream


object Utils{
    private var memoryCache: LruCache<String, ByteArray?>

    private val cacheSize = (Runtime.getRuntime().maxMemory()).toInt() / 10

    init {
        memoryCache = object : LruCache<String, ByteArray?>(cacheSize) {
            override fun sizeOf(key: String, image: ByteArray): Int {
                return image.size
            }
        }
    }

    fun loadBitmap(id : String) : Bitmap? = decodeByteArray(memoryCache.get(id))

    fun saveBitmap(id : String, bitmap : Bitmap?){
        if(bitmap != null)
            memoryCache.put(id, encodeToByteArray(bitmap))
    }

    private fun encodeToByteArray(image: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun decodeByteArray(input: ByteArray?): Bitmap? {
        return if (input != null)
            BitmapFactory.decodeByteArray(input, 0, input.size)
        else
            null
    }

}