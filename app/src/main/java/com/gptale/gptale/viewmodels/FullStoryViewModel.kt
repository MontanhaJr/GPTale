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
import com.gptale.gptale.models.FullStoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.FullStoryRepository
import com.gptale.gptale.retrofit.APIListener

class FullStoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FullStoryRepository(application.applicationContext)

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    var story: FullStoryModel? = null

    fun requestFullStory(idStory: Int) {
        repository.requestFullStory(idStory, object : APIListener<FullStoryModel> {

            override fun onSuccess(result: FullStoryModel) {
                story = result
                _storyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _storyRequest.value = RequestValidation(message)
            }
        })
    }

    fun copyStory(requireContext: Context) {
        val clipboardManager = getSystemService(requireContext, ClipboardManager::class.java) as ClipboardManager
        val clipData = ClipData.newPlainText("text", story?.fullStory.toString())
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(requireContext, "Copiado para Área de Transferência", Toast.LENGTH_LONG).show()
    }


}