package com.yesayasoftware.learning.data.db.entity

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")

data class PostEntry(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @Embedded(prefix = "category_")
    val category: CategoryEntry,
    @Embedded(prefix = "creator_")
    val creator: UserEntry,
    @SerializedName("comment_count")
    val comment_count: Int,
    @SerializedName("visits")
    val visits: Int,
    @SerializedName("thumbnail_path")
    val thumbnail_path: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at:  String? = null
)