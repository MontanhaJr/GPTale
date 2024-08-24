package com.gptale.gptale.repository

import com.gptale.gptale.api.ApiClient
import com.gptale.gptale.api.StoryService
import com.gptale.gptale.dao.StoryDao
import com.gptale.gptale.models.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepository(private val storyDao: StoryDao) {

    suspend fun getStoryById(id: Long): Story {
        return withContext(Dispatchers.IO) {
            storyDao.getById(id)
        }
    }

    suspend fun insertStory(story: Story) {
        withContext(Dispatchers.IO) {
            storyDao.insert(story)
        }
    }

    suspend fun updateStory(story: Story) {
        withContext(Dispatchers.IO) {
            storyDao.update(story)
        }
    }
}