package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.models.FullHistoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.FullHistoryRepository
import com.gptale.gptale.retrofit.APIListener

class FullHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FullHistoryRepository(application.applicationContext)

    private val _historyRequest = MutableLiveData<RequestValidation>()
    val historyRequest: LiveData<RequestValidation> = _historyRequest

    var history: FullHistoryModel? = null

    fun requestFullHistory(idHistory: Int) {
        repository.requestFullHistory(idHistory, object : APIListener<FullHistoryModel> {

            override fun onSuccess(result: FullHistoryModel) {
                history = result
                _historyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _historyRequest.value = RequestValidation(message)
            }
        })
    }


}