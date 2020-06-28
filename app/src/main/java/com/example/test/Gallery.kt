package com.example.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gallery.*


class Gallery : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val position = intent.getIntExtra("id", -1)

        setDataToAdapter(position)

        layout_back.setOnClickListener {
            onBackPressed()
        }
    }


    private fun setDataToAdapter(position: Int) {

        val currentId = data.usersList()!![position].id
        val currentAlbum  = data.getAlbumsByUserId(currentId)
        Log.d("tag", currentAlbum.toString())
        val listPicture = data.getPicturesByAlbumId(currentAlbum)

        if(listPicture?.size ?: 0 == 0 || currentAlbum == null)
            empty_gallery.visibility = View.VISIBLE
        else
            list_view_gallery.adapter = GalleryAdapter(this, listPicture!!)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
    }
}

