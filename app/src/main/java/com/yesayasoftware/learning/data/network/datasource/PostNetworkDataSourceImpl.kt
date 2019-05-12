package com.yesayasoftware.learning.data.network.datasource

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yesayasoftware.learning.data.network.ApiService
import com.yesayasoftware.learning.data.network.RetrofitBuilder
import com.yesayasoftware.learning.data.network.response.PostResponse
import com.yesayasoftware.learning.internal.NoConnectivityException
import com.yesayasoftware.learning.internal.TokenManager

class PostNetworkDataSourceImpl(
    private val retrofitBuilder: RetrofitBuilder,
    context: Context
) : PostNetworkDataSource {
    private val appContext = context.applicationContext

    private val _downloadedPosts = MutableLiveData<PostResponse>()
    override val downloadedPosts: LiveData<PostResponse>
        get() = _downloadedPosts

    override suspend fun fetchPosts() {
        try {
            val fetchedPosts = retrofitBuilder.createServiceWithAuth(ApiService::class.java, TokenManager.getInstance(appContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)))
                .getPostsAsync()
                .await()
            _downloadedPosts.postValue(fetchedPosts)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}