package com.example.test

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class UsersAdapter(context: Context, private var data: List<User>) :
    ArrayAdapter<User>(context, R.layout.list_item, data) {

    class ViewHolder {
        var name: TextView? = null
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): User? {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val currentUser = data[position]
        val holder : ViewHolder
        var myView: View? = convertView
        if (convertView == null) {
            myView = inflater!!.inflate(R.layout.list_item, null)!!
            holder = ViewHolder()
            holder.name = myView.findViewById(R.id.name)

            myView.tag = holder

        } else {
            holder = myView!!.tag as ViewHolder
        }

        holder.name!!.text = currentUser.name

        myView.setOnClickListener {
            val intent = Intent(context, Gallery::class.java)
            intent.putExtra("id", position)
            context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.trans_right_in, R.anim.trans_right_out).toBundle())
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
