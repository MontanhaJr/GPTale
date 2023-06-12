package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.Constants
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.OptionModel
import com.gptale.gptale.retrofit.APIListener
import com.gptale.gptale.retrofit.StoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(val context: Context) {

    private val retrofitClient = RetrofitClient().createService(StoryService::class.java)

    fun sendOptionSelected(optionModel: OptionModel, listener: APIListener<StoryModel>) {
        val call = retrofitClient.sendOption(optionModel)
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