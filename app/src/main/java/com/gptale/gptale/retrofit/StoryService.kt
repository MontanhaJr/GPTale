package com.gptale.gptale.retrofit

import com.gptale.gptale.models.FullStoryModel
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.OptionModel
import com.gptale.gptale.models.StartModel
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryService {

    @POST("sendOption")
    fun sendOption(@Body optionModel: OptionModel): Call<StoryModel>

    @POST("startHistory")
    fun startHistory(@Body startModel: StartModel): Call<StoryModel>

    @GET("saveHistory/{id}")
    fun saveHistory(@Path("id") idHistory: Int): Call<FullStoryModel>

}