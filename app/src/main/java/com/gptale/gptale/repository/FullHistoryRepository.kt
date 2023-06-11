package com.gptale.gptale.repository

import android.content.Context
import com.gptale.gptale.R
import com.gptale.gptale.constants.Constants
import com.gptale.gptale.models.FullHistoryModel
import com.gptale.gptale.retrofit.APIListener
import com.gptale.gptale.retrofit.HistoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullHistoryRepository(val context: Context) {
    private val retrofitClient = RetrofitClient().createService(HistoryService::class.java)

    fun requestFullHistory(idHistory: Int, listener: APIListener<FullHistoryModel>) {
        val call = retrofitClient.saveHistory(idHistory)
        call.enqueue(object : Callback<FullHistoryModel> {
            override fun onResponse(call: Call<FullHistoryModel>, response: Response<FullHistoryModel>) {
                if (response.code() == Constants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(context.getString(R.string.start_history_error))
                }
            }

            override fun onFailure(call: Call<FullHistoryModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.unexpected_error))
            }
        })
    }
}