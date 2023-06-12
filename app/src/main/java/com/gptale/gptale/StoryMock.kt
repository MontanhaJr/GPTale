package com.gptale.gptale

import android.content.Context
import com.gptale.gptale.models.StoryModel

class StoryMock() {
    companion object {
        fun historyListMock(context: Context): List<StoryModel> {
            val historyList = mutableListOf<StoryModel>()
            val history = StoryModel(
                id = 1,
                title = "O marinheiro que tinha medo do Mar",
                gender = "Aventura",
                paragraph = context.resources.getString(R.string.paragraph_sample),
                options = listOf(
                    context.resources.getString(R.string.option1_sample),
                    context.resources.getString(R.string.option2_sample),
                    context.resources.getString(R.string.option3_sample),
                    context.resources.getString(R.string.option4_sample)
                ),
                fullStory = null
            )

            historyList.add(history)
            history.id = 2
            historyList.add(history)
            history.id = 3
            historyList.add(history)
            return historyList
        }
    }
}