package com.yesayasoftware.learning.ui.posts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesayasoftware.learning.data.repository.YesayaSoftwareRepository

class PostListViewModelFactory(
    private val yesayaSoftwareRepository: YesayaSoftwareRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(
            yesayaSoftwareRepository
        ) as T
    }
}