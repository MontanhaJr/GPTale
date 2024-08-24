package com.gptale.gptale.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gptale.gptale.BuildConfig
import com.gptale.gptale.Util.StringUtils
import com.gptale.gptale.Util.StringUtils.parseResponse
import com.gptale.gptale.api.ApiClient
import com.gptale.gptale.api.ChatGPTMessage
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.StartRepository
import com.gptale.gptale.api.ChatGPTRequest
import com.gptale.gptale.api.ChatGPTResponse
import com.gptale.gptale.api.ChatGPTService
import com.gptale.gptale.models.Story
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartViewModel(private val repository: StartRepository) : ViewModel() {

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    lateinit var createdStory: Story

    private var messages = mutableListOf<ChatGPTMessage>()

    private fun addStory(story: Story) {
        viewModelScope.launch {
            createdStory = repository.insertStory(story)
            _storyRequest.value = RequestValidation()
        }
    }

    fun createStory(title: String, gender: String) {
        val chatGPTService = ApiClient.retrofit.create(ChatGPTService::class.java)
        val totalParagraphs = 1

        val prompt = StringUtils.defaultPrompt(title, gender, totalParagraphs)

        messages.addAll(
            listOf(
                ChatGPTMessage(
                    role = "system",
                    content = "Você é um contador de histórias muito criativo"
                ),
                ChatGPTMessage(
                    role = "user",
                    content = prompt
                )
            )
        )
        val request = ChatGPTRequest(messages = messages)

        chatGPTService.getCompletion(request = request).enqueue(object :
            Callback<ChatGPTResponse> {
            override fun onResponse(
                call: Call<ChatGPTResponse>,
                response: Response<ChatGPTResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val completion = response.body()?.choices?.get(0)?.message?.content
                    // Exibir ou usar o resultado da resposta da API
                    val paragraph = parseResponse(completion!!)
                    println("Parágrafo: ${paragraph.paragraph}")
                    paragraph.options.forEach { println("Opções: $it") }
                    println("Pergunta: ${paragraph.question}")
                    addStory(
                        Story(
                            title = title,
                            gender = gender,
                            paragraph = paragraph.paragraph,
                            totalParagraphs = totalParagraphs,
                            options = paragraph.options,
                            fullStory = paragraph.paragraph
                        )
                    )
                    println("Start Total Tokens: ${response.body()?.usage?.total_tokens!!}")
                } else {
                    // Tratar erro
                    val errorBody = response.errorBody()?.string()
                    println("Erro: $errorBody")
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                // Tratar falha na requisição
                println("Falha: ${t.message}")
            }
        })
    }
}