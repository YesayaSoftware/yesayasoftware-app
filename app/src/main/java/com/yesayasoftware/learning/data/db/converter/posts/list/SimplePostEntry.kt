package com.yesayasoftware.learning.data.db.converter.posts.list

import androidx.room.ColumnInfo
import com.yesayasoftware.learning.data.db.converter.ConvertPostEntry
import org.threeten.bp.LocalDate

data class SimplePostEntry (
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "created_at")
    override val created_at: LocalDate,
    @ColumnInfo(name = "updated_at")
    override val updated_at: LocalDate,
    @ColumnInfo(name = "creator_name")
    override val creatorName: String,
    @ColumnInfo(name = "thumbnail_path")
    override val thumbnailPath: String
) : ConvertPostEntry