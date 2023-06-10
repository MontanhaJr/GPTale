package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.retrofit.HistoryService
import com.gptale.gptale.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofitClient = RetrofitClient().createService(HistoryService::class.java)

    fun createNewHistory(title: String, gender: String) {
        var call = retrofitClient.startHistory(title, gender)
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