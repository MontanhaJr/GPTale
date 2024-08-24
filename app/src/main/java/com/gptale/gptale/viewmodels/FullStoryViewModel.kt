package com.gptale.gptale.viewmodels

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gptale.gptale.Util.StringUtils
import com.gptale.gptale.models.FullStoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.FullStoryRepository
import com.gptale.gptale.api.APIListener
import com.gptale.gptale.api.ChatGPTMessage
import com.gptale.gptale.api.ChatGPTRequest
import com.gptale.gptale.api.ChatGPTResponse
import com.gptale.gptale.models.Story
import com.gptale.gptale.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullStoryViewModel(private val repository: FullStoryRepository) : ViewModel() {

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    var story: Story? = null

    fun fetchStoryById(idStory: Long) {
        viewModelScope.launch {
            story = repository.getStoryById(idStory)
            _storyRequest.value = RequestValidation()
        }
    }

    fun copyStory(requireContext: Context) {
        val clipboardManager = getSystemService(requireContext, ClipboardManager::class.java) as ClipboardManager
        val clipData = ClipData.newPlainText("text", story?.fullStory.toString())
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(requireContext, "Copiado para Área de Transferência", Toast.LENGTH_LONG).show()
    }
}