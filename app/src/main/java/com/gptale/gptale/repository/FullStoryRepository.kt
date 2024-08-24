package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.Constants
import com.gptale.gptale.models.FullStoryModel
import com.gptale.gptale.api.APIListener
import com.gptale.gptale.api.ApiClient
import com.gptale.gptale.api.StoryService
import com.gptale.gptale.dao.StoryDao
import com.gptale.gptale.models.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullStoryRepository(private val storyDao: StoryDao) {
    suspend fun getStoryById(id: Long): Story {
        return withContext(Dispatchers.IO) {
            storyDao.getById(id)
        }
    }
}