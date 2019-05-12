package com.yesayasoftware.learning.data.network.response

import com.google.gson.annotations.SerializedName
import com.yesayasoftware.learning.data.db.entity.PostEntry

data class PostContainer(
    @SerializedName("post_list")
    val entries: List<PostEntry>
)