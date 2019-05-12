package com.yesayasoftware.learning.data.repository

import androidx.lifecycle.LiveData
import com.yesayasoftware.learning.data.db.converter.ConvertPostEntry
import com.yesayasoftware.learning.data.db.dao.PostDao
import com.yesayasoftware.learning.data.network.datasource.PostNetworkDataSource
import com.yesayasoftware.learning.data.network.response.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class YesayaSoftwareRepositoryImpl(
    private val postDao : PostDao,
    private val postNetworkDataSource: PostNetworkDataSource
) : YesayaSoftwareRepository {
    init {
        postNetworkDataSource.apply {
            downloadedPosts.observeForever { newPosts ->
                persistFetchedPosts(newPosts)
            }
        }
    }
    override suspend fun getPostList(): LiveData<out List<ConvertPostEntry>> {
        return withContext(Dispatchers.IO) {
            initPostData()
            return@withContext postDao.getPosts()
        }
    }

    private fun persistFetchedPosts(fetchedPosts: PostResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val postList = fetchedPosts.postEntries.entries
            postDao.insert(postList)
        }
    }

    private suspend fun initPostData() {
        fetchPosts()
    }

    private suspend fun fetchPosts() {
        postNetworkDataSource.fetchPosts()
    }
}