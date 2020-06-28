package com.example.test

import android.R.attr.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class ImageLoader {

    var executorService: ExecutorService = Executors.newFixedThreadPool(5)
    var handler = Handler()


    fun displayImage(picture: Picture, holder: GalleryAdapter.ViewHolder) {
        queuePhoto(picture, holder)
    }

    private fun queuePhoto(picture: Picture, holder: GalleryAdapter.ViewHolder) {
        executorService.submit(PhotosLoader(picture, holder))
    }


    private fun getBitmap(url: String, picture: Picture): Bitmap? {
        return try {
            val connection: HttpsURLConnection = URL(url).openConnection() as HttpsURLConnection
            connection.setRequestProperty("User-Agent", "your-user-agent")
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val bmp = BitmapFactory.decodeStream(input)

            Utils.saveBitmap(picture.id, bmp)

            bmp
        } catch (e: IOException) {
            Log.d("tag", e.toString())
            null
        }
    }

    internal inner class PhotosLoader(var picture: Picture, var holder: GalleryAdapter.ViewHolder) : Runnable {
        override fun run() {
            try {
                val bmp = getBitmap(picture.url, picture)
                val bd = BitmapDisplayer(bmp, holder)
                handler.post(bd)
            } catch (th: Throwable) {
                th.printStackTrace()
            }
        }

    }

    internal class BitmapDisplayer(var bitmap: Bitmap?, var holder: GalleryAdapter.ViewHolder) : Runnable {
        override fun run() {
            if (bitmap != null) {
                holder.progressBar!!.visibility = ProgressBar.GONE
                holder.img!!.setImageBitmap(bitmap)
            }
            else {
                holder.progressBar!!.visibility = ProgressBar.GONE
                holder.img!!.setBackgroundResource(R.drawable.load_error)
            }
        }

    }

}