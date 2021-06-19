package com.example.firebase.domain.home

import com.example.firebase.core.Resource
import com.example.firebase.data.model.Post
import com.example.firebase.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl( private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()
}