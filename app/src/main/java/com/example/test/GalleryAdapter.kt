package com.example.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView


class GalleryAdapter(context: Context, private var data: List<Picture>) :
    ArrayAdapter<Picture>(context, R.layout.item_gallery, data) {

    class ViewHolder {
        var img: ImageView? = null
        var title: TextView? = null
        var progressBar : ProgressBar? = null
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Picture? {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val currentPicture = data[position]
        val holder : ViewHolder
        var myView: View? = convertView
        if (convertView == null) {
            myView = inflater!!.inflate(R.layout.item_gallery, null)!!
            holder = ViewHolder()
            holder.title = myView.findViewById(R.id.gallery_title)
            holder.img = myView.findViewById(R.id.gallery_image)
            holder.progressBar = myView.findViewById(R.id.gallery_progress_bar)

            myView.tag = holder

        } else {
            holder = myView!!.tag as ViewHolder
        }

        holder.title!!.text = currentPicture.title


        val bitmap = Utils.loadBitmap(currentPicture.id)
        if(bitmap != null){
            holder.progressBar!!.visibility = ProgressBar.GONE
            holder.img!!.setImageBitmap(bitmap)
        }
        else{
            holder.img!!.setImageBitmap(Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888))
            holder.progressBar!!.visibility = ProgressBar.VISIBLE
            ImageLoader().displayImage(data[position], holder)
        }

        return myView
    }


    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }


}

