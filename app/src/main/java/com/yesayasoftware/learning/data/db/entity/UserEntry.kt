package com.yesayasoftware.learning.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class UserEntry(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val email: String,
    val thumbnail_path: String? = null,
    val email_verified_at: String,
    val created_at: String,
    val updated_at:  String? = null
)