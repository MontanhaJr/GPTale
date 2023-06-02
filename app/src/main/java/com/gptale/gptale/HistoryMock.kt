package com.gptale.gptale

import android.content.Context
import com.gptale.gptale.models.HistoryModel

class HistoryMock() {
    companion object {
        fun historyListMock(context: Context): List<HistoryModel> {
            val historyList = mutableListOf<HistoryModel>()
            val history = HistoryModel(
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
                fullHistory = null
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