package com.example.firebase.domain.home


import com.example.firebase.core.Resource
import com.example.firebase.data.model.Post

interface HomeScreenRepo {

    suspend fun getLatestPosts(): Resource<List<Post>>
}