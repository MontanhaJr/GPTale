package com.gptale.gptale.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gptale.gptale.Util.StringUtils
import com.gptale.gptale.Util.StringUtils.optionSelected
import com.gptale.gptale.Util.StringUtils.parseResponse
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.StoryRepository
import com.gptale.gptale.api.ApiClient
import com.gptale.gptale.api.ChatGPTMessage
import com.gptale.gptale.api.ChatGPTRequest
import com.gptale.gptale.api.ChatGPTResponse
import com.gptale.gptale.api.ChatGPTService
import com.gptale.gptale.models.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {

    private val chatGPTService = ApiClient.retrofit.create(ChatGPTService::class.java)

    private val messages = mutableListOf<ChatGPTMessage>()

    lateinit var story: Story
    private var paragraphCounter = 0
    private var finalizeStory = false
    private var contadorDeTokens = 0

    fun updateStory(newParagraph: String) {
        viewModelScope.launch {
            val storyUpdate = repository.getStoryById(story.uid)
            storyUpdate.fullStory += "\n" + newParagraph
            repository.updateStory(storyUpdate)
        }
    }

    fun processStory(startedStory: Story) {
        messages.addAll(
            listOf(
                ChatGPTMessage(
                    role = "system",
                    content = "Você é um contador de histórias muito criativo"
                ),
                ChatGPTMessage(
                    "user",
                    StringUtils.defaultPrompt(
                        startedStory.title,
                        startedStory.gender,
                        startedStory.totalParagraphs
                    )
                ),
                ChatGPTMessage(
                    role = "system",
                    content = startedStory.fullStory
                )
            )
        )
    }

    fun addStory(story: Story) {
        viewModelScope.launch {
            repository.insertStory(story)
//            fetchAllStories()  // Refresh list after adding new story
        }
    }

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    fun sendOption(optionSelected: Int) {
        finalizeStory = story.totalParagraphs <= paragraphCounter
        messages.add(ChatGPTMessage("user", optionSelected(optionSelected, finalizeStory)))

        val request = ChatGPTRequest(
            messages = messages
        )

        println("Request Messages: Início")
        request.messages.forEach {
            println(it)
        }
        println("Request Messages: Fim")
        chatGPTService.getCompletion(request).enqueue(object : Callback<ChatGPTResponse> {
            override fun onResponse(
                call: Call<ChatGPTResponse>,
                response: Response<ChatGPTResponse>
            ) {
                contadorDeTokens += response.body()?.usage?.total_tokens!!
                if (response.isSuccessful && response.body() != null) {
                    val responseContent = response.body()?.choices?.get(0)?.message?.content
                    // Exibir ou usar o resultado da resposta da API
                    val paragraph = parseResponse(responseContent!!)
                    story = Story(
                        story.uid,
                        story.title,
                        story.gender,
                        paragraph.paragraph,
                        story.totalParagraphs,
                        paragraph.options,
                        paragraph.paragraph
                    )
                    paragraphCounter++
                    messages.add(ChatGPTMessage("system", responseContent))
                    println("Retorno: $responseContent")
                    println("Paragrafo Retorno: " + paragraph.paragraph)
                    println("Options Retorno: " + paragraph.options)
                    println("Pergunta: ${paragraph.question}")
                    _storyRequest.value = RequestValidation()
                    println("Total tokens: $contadorDeTokens")
                    updateStory(paragraph.paragraph)
                } else {
                    // Tratar erro
                    val errorBody = response.errorBody()?.string()
                    println("Erro: $errorBody")
                    _storyRequest.value = errorBody?.let { RequestValidation(it) }
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                // Tratar falha na requisição
                println("Falha: ${t.message}")
            }
        })
    }
}