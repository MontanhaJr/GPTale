package com.gptale.gptale.api

import com.gptale.gptale.BuildConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface ChatGPTService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    fun getCompletion(
        @Body request: ChatGPTRequest,
        @Header("Authorization") authHeader: String = "Bearer ${BuildConfig.CHATGPT_API_TOKEN}"
    ): Call<ChatGPTResponse>
}

data class ChatGPTRequest(
    val model: String = "gpt-3.5-turbo-0125",
    val messages: List<ChatGPTMessage>,
)

data class ChatGPTMessage(
    val role: String,
    val content: String
)

data class ChatGPTResponse(
    val choices: List<Choice>,
    val created: Long,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)

data class Choice(
    val finish_reason: String,
    val index: Int,
    val message: Message,
    val logprobs: Any?
)

data class Message(
    val content: String,
    val role: String
)

data class Usage(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)

