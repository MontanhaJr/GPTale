package com.gptale.gptale.retrofit

import com.gptale.gptale.dto.ParagraphResponse
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.models.StartModel
import retrofit2.Call
import retrofit2.http.*

interface HistoryService {

    @GET("paragraph")
    fun getNewParagraph(): Call<List<ParagraphResponse>>

    @POST("startHistory")
    fun startHistory(
        @Body startModel: StartModel
    ): Call<HistoryModel>

}