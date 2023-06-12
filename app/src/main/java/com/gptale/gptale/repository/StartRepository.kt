package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.Constants
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.retrofit.APIListener
import com.gptale.gptale.retrofit.StoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartRepository(val context: Context) {

    private val retrofitClient = RetrofitClient().createService(StoryService::class.java)

    fun createNewStory(startModel: StartModel, listener: APIListener<StoryModel>) {
        val call = retrofitClient.startStory(startModel)
        call.enqueue(object : Callback<StoryModel> {
            override fun onResponse(call: Call<StoryModel>, response: Response<StoryModel>) {
                if (response.code() == Constants.HTTP.CREATED) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(context.getString(R.string.start_history_error))
                }
            }

            override fun onFailure(call: Call<StoryModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.unexpected_error))
            }
        })
    }
}