package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.TaskConstants
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.retrofit.APIListener
import com.gptale.gptale.retrofit.HistoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartRepository(val context: Context) {

    private val retrofitClient = RetrofitClient().createService(HistoryService::class.java)

    fun createNewHistory(startModel: StartModel, listener: APIListener<HistoryModel>) {
        val call = retrofitClient.startHistory(startModel)
        call.enqueue(object : Callback<HistoryModel> {
            override fun onResponse(call: Call<HistoryModel>, response: Response<HistoryModel>) {
                if (response.code() == TaskConstants.HTTP.CREATED) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(context.getString(R.string.start_history_error))
                }
            }

            override fun onFailure(call: Call<HistoryModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.unexpected_error))
            }
        })
    }
}