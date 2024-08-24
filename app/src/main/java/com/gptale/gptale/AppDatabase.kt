package com.gptale.gptale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gptale.gptale.Util.Converters
import com.gptale.gptale.dao.StoryDao
import com.gptale.gptale.models.Story

@Database(entities = [Story::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}