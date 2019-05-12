package com.yesayasoftware.learning.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yesayasoftware.learning.data.db.entity.CategoryEntry

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: List<CategoryEntry>)

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryEntry>>

    @Query("SELECT count(id) FROM categories")
    fun countCategories(): Int

    @Query("DELETE FROM categories WHERE id = :id")
    fun deleteCategories(id: Int)
}