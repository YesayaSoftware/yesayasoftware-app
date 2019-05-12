package com.yesayasoftware.learning.data.repository

import androidx.lifecycle.LiveData
import com.yesayasoftware.learning.data.db.converter.ConvertPostEntry

interface YesayaSoftwareRepository {
    suspend fun getPostList(): LiveData<out List<ConvertPostEntry>>
}