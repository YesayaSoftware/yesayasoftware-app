package com.yesayasoftware.learning.data.network.datasource

import androidx.lifecycle.LiveData
import com.yesayasoftware.learning.data.network.response.PostResponse

interface PostNetworkDataSource {
    val downloadedPosts: LiveData<PostResponse>

    suspend fun fetchPosts()
}