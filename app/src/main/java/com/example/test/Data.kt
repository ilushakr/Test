package com.example.test


data class Data(private var users : List<User>?, private var albums: List<Album>?, private val pictures: List<Picture>?){

    fun usersList() : List<User>? = users

    fun albumsList() : List<Album>? = albums

    fun picturesList() : List<Picture>? = pictures

    fun getAlbumsByUserId(userId: String) : List<Album>?{
        return albums?.filter {
            it.userId == userId
        }
    }

    fun getPicturesByAlbumId(listAlbum: List<Album>?) : List<Picture>?{
        val listPicture = mutableListOf<Picture>()
        return if(listAlbum != null){
            listAlbum.forEach { album ->
                getPictureById(album.id)?.forEach{picture ->
                    listPicture.add(picture)
                }
            }
            listPicture
        } else null
    }

    private fun getPictureById(id : String) : List<Picture>?{
        return pictures?.filter {
            it.albumId == id
        }
    }

}


data class User(var id : String, var name : String)

data class Album(var userId : String, var id : String)

data class Picture(var albumId : String, var title : String, var url : String, var id : String)

