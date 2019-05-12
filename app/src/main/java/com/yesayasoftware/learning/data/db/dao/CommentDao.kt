package com.yesayasoftware.learning.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yesayasoftware.learning.data.db.entity.CommentEntry

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: List<CommentEntry>)

    @Query("SELECT * FROM comments")
    fun getComments(): LiveData<List<CommentEntry>>

    @Query("SELECT count(id) FROM comments")
    fun countComments(): Int

    @Query("DELETE FROM comments WHERE id = :id")
    fun deleteComment(id: Int)
}