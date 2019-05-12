package com.yesayasoftware.learning.data.network.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("posts")
    val postEntries: PostContainer
)