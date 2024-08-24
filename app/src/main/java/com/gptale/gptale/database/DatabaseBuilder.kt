package com.gptale.gptale.database

import android.content.Context
import androidx.room.Room
import com.gptale.gptale.AppDatabase

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "story-database3"
                ).build()
            }
        }
        return INSTANCE!!
    }
}
