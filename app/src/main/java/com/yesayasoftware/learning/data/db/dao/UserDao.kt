package com.yesayasoftware.learning.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yesayasoftware.learning.data.db.entity.UserEntry

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<UserEntry>)

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UserEntry>>

    @Query("SELECT count(id) FROM users")
    fun countUsers(): Int

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUsers(id: Int)
}