package com.gptale.gptale.retrofit

import com.gptale.gptale.dto.ParagraphResponse
import com.gptale.gptale.models.HistoryModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HistoryService {

    @GET("paragraph")
    fun getNewParagraph(): Call<List<ParagraphResponse>>

    @POST("startHistory")
    @FormUrlEncoded
    fun startHistory(
        @Field("title") title: String,
        @Field("gender") gender: String
    ): Call<HistoryModel>

}