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

    private val _historyRequest = MutableLiveData<RequestValidation>()
    val historyRequest: LiveData<RequestValidation> = _historyRequest

    var history: FullStoryModel? = null

    fun requestFullHistory(idHistory: Int) {
        repository.requestFullHistory(idHistory, object : APIListener<FullStoryModel> {

            override fun onSuccess(result: FullStoryModel) {
                history = result
                _historyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _historyRequest.value = RequestValidation(message)
            }
        })
    }

    fun copyHistory(requireContext: Context) {
        val clipboardManager = getSystemService(requireContext, ClipboardManager::class.java) as ClipboardManager
        val clipData = ClipData.newPlainText("text", history?.fullHistory.toString())
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(requireContext, "Copiado para Área de Transferência", Toast.LENGTH_LONG).show()
    }


}