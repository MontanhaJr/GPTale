package com.gptale.gptale.retrofit

import com.gptale.gptale.dto.ParagraphResponse
import retrofit2.Call
import retrofit2.http.GET

interface HistoryService {

    @GET("paragraph")
    fun getNewParagraph(): Call<List<ParagraphResponse>>

}