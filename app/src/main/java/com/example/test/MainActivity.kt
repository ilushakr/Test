package com.example.test

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


private var albums : List<Album>? = null
private var pictures : List<Picture>? = null
private var users : List<User>? = null
lateinit var data : Data


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataLoad(this).execute()
    }
}

class DataLoad(private val context: Context) : AsyncTask<Void, Void, Data>() {
    private val errorText = (context as Activity).findViewById<TextView>(R.id.error_text)
    private val refreshButton = (context as Activity).findViewById<Button>(R.id.refresh_button)
    private var progressBar = (context as Activity).findViewById<ProgressBar>(R.id.progress_bar)
    private val usersList = (context as Activity).findViewById<ListView>(R.id.users_list)

    override fun onPreExecute() {
        super.onPreExecute()
        errorText.visibility = View.GONE
        refreshButton.visibility = View.GONE
        progressBar.visibility = ProgressBar.VISIBLE
    }
    override fun doInBackground(vararg params: Void?): Data {

        users = saveUsers()
        albums = saveAlbums()
        pictures = savePictures()
        data = Data(users, albums, pictures)
//        Log.d("tag"," - " + users!!.size + " - " + albums!!.size + " - " + pictures!!.size)
        return data

    }

    override fun onPostExecute(data: Data) {
        progressBar.visibility = ProgressBar.INVISIBLE

        super.onPostExecute(data)

        if(data.usersList() != null) {
            errorText.visibility = View.GONE
            refreshButton.visibility = View.GONE
            usersList.adapter = UsersAdapter(context,  data.usersList()!!)
        }
        else{
            errorText.visibility = View.VISIBLE
            refreshButton.visibility = View.VISIBLE
            refreshButton.setOnClickListener {
                DataLoad(context).execute()
            }

        }
    }

    private fun saveUsers() : List<User>?{
        return try {
            var tmp = URL("https://jsonplaceholder.typicode.com/users").readText()
            tmp = "{\"users\":$tmp}"

            val users = mutableListOf<User>()
            val jsnobject = JSONObject(tmp)
            val jsonArray: JSONArray = jsnobject.getJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                val explrObject = jsonArray.getJSONObject(i)
                val user = User(explrObject.getString("id"), explrObject.getString("name"))
                users.add(user)
            }
            return users

        }catch (e : Exception){
            Log.d("tag", e.toString())
            null
        }
    }

    private fun saveAlbums() : List<Album>? {
        return try {
            var tmp = URL("https://jsonplaceholder.typicode.com/albums").readText()
            tmp = "{\"albums\":$tmp}"

            val albums = mutableListOf<Album>()
            val jsnobject = JSONObject(tmp)
            val jsonArray: JSONArray = jsnobject.getJSONArray("albums")
            for (i in 0 until jsonArray.length()) {
                val explrObject = jsonArray.getJSONObject(i)
                val album = Album(explrObject.getString("userId"), explrObject.getString("id"))
                albums.add(album)
            }
            return albums

        }catch (e : Exception){
            Log.d("tag", e.toString())
            null
        }
    }

    private fun savePictures() : List<Picture>?{
        return try {
            var tmp = URL("https://jsonplaceholder.typicode.com/photos").readText()
            tmp = "{\"pictures\":$tmp}"

            val pictures = mutableListOf<Picture>()
            val jsnobject = JSONObject(tmp)
            val jsonArray: JSONArray = jsnobject.getJSONArray("pictures")
            for (i in 0 until jsonArray.length()) {
                val explrObject = jsonArray.getJSONObject(i)
                val picture = Picture(explrObject.getString("albumId"), explrObject.getString("title"), explrObject.getString("url"), explrObject.getString("id"))
                pictures.add(picture)
            }
            return pictures

        }catch (e : Exception){
            Log.d("tag", e.toString())
            null
        }
    }

}

