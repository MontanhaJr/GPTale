package com.gptale.gptale.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer sk-proj-PxyL8GD257yyfEG5yhGkT3BlbkFJ1LtimdQI938N5O6e7NWo"
    )
    @POST("v1/chat/completions")
    fun getCompletion(@Body request: ChatGPTRequest): Call<ChatGPTResponse>
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

