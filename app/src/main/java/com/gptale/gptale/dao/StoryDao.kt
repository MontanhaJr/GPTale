package com.gptale.gptale.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gptale.gptale.models.Story

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getAll(): List<Story>

    @Query("SELECT * FROM story WHERE uid = :id")
    fun getById(id: Long): Story

    @Query("SELECT * FROM story WHERE title = :title")
    fun getByTitle(title: String): Story

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(story: Story): Long

    @Delete
    suspend fun delete(story: Story)

    @Update
    suspend fun update(story: Story)

}