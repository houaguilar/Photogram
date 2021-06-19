package com.example.firebase.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    @ServerTimestamp
    var created_at: Date? = null,
    val post_image: String = "",
    val post_description: String = "",
    val uid: String

//    @SerializedName("profile_picture")
//val profilePicture: String = "",
//@SerializedName("profile_name")
//val profileName: String = "",
//@SerializedName("post_timestamp")
//val postTimestamp: Timestamp? = null,
//@SerializedName("post_image")
//val postImage: String = ""
)
