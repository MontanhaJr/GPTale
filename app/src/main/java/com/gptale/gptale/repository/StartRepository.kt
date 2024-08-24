package com.gptale.gptale.repository

import com.gptale.gptale.api.ApiClient
import com.gptale.gptale.api.StoryService
import com.gptale.gptale.dao.StoryDao
import com.gptale.gptale.models.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StartRepository(private val storyDao: StoryDao) {

    private suspend fun getStoryById(id: Long): Story {
        return withContext(Dispatchers.IO) {
            storyDao.getById(id)
        }
    }

    suspend fun insertStory(story: Story): Story {
        val storyId = storyDao.insert(story)
        return getStoryById(storyId)
    }
}