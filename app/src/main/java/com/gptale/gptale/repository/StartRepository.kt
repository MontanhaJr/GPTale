package com.gptale.gptale.repository

import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.retrofit.HistoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartRepository {

    private val retrofitClient = RetrofitClient().createService(HistoryService::class.java)

    fun createNewHistory(startModel: StartModel) {
        var call = retrofitClient.startHistory(startModel)
        call.enqueue(object : Callback<HistoryModel> {
            override fun onResponse(call: Call<HistoryModel>, response: Response<HistoryModel>) {
                if (response.isSuccessful) {
                    val history = response.body()
                }
            }

            override fun onFailure(call: Call<HistoryModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}