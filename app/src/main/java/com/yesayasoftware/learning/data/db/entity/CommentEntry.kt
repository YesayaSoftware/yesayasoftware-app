package com.yesayasoftware.learning.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")

data class CommentEntry(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val body: String,
    @Embedded(prefix = "post_")
    val post: PostEntry,
    @Embedded(prefix = "user_")
    val user: UserEntry,
    val created_at: String,
    val updated_at:  String? = null
)