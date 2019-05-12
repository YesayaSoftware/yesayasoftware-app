package com.yesayasoftware.learning.data.db.entity

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")

data class CategoryEntry(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val slug: String,
    val name: String,
    val description: String,
    @Embedded(prefix = "creator_")
    val creator: UserEntry,
    val created_at: String,
    val updated_at: String? = null
)