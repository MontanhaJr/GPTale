package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.Constants
import com.gptale.gptale.models.FullStoryModel
import com.gptale.gptale.retrofit.APIListener
import com.gptale.gptale.retrofit.StoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullStoryRepository(val context: Context) {
    private val retrofitClient = RetrofitClient().createService(StoryService::class.java)

    fun requestFullHistory(idHistory: Int, listener: APIListener<FullStoryModel>) {
        val call = retrofitClient.saveHistory(idHistory)
        call.enqueue(object : Callback<FullStoryModel> {
            override fun onResponse(call: Call<FullStoryModel>, response: Response<FullStoryModel>) {
                if (response.code() == Constants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(context.getString(R.string.start_history_error))
                }
            }

            override fun onFailure(call: Call<FullStoryModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.unexpected_error))
            }
        })
    }
}