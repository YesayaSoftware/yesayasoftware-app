package com.yesayasoftware.learning.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yesayasoftware.learning.data.db.converter.posts.list.SimplePostEntry
import com.yesayasoftware.learning.data.db.entity.PostEntry

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: List<PostEntry>)

    @Query("SELECT * FROM posts")
    fun getPosts(): LiveData<List<SimplePostEntry>>

    @Query("SELECT count(id) FROM posts")
    fun countPosts(): Int

    @Query("DELETE FROM posts WHERE id = :id")
    fun deletePost(id: Int)
}