package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.models.OptionModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.HistoryRepository
import com.gptale.gptale.retrofit.APIListener

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HistoryRepository(application.applicationContext)

    private val _historyRequest = MutableLiveData<RequestValidation>()
    val historyRequest: LiveData<RequestValidation> = _historyRequest

    var history: HistoryModel? = null

    fun sendOption(idHistory: Int, optionSelected: Int) {
        repository.sendOptionSelected(OptionModel(idHistory, optionSelected), object : APIListener<HistoryModel> {

            override fun onSuccess(result: HistoryModel) {
                history = result
                _historyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _historyRequest.value = RequestValidation(message)
            }
        })
    }


}