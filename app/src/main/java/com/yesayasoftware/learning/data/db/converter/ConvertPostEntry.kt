package com.yesayasoftware.learning.data.db.converter

import org.threeten.bp.LocalDate

interface ConvertPostEntry {
    val title: String
    val created_at: LocalDate
    val updated_at: LocalDate
    val creatorName: String
    val thumbnailPath: String
}