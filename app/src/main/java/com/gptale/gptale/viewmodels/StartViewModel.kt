package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.repository.StartRepository
import com.gptale.gptale.retrofit.APIListener

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StartRepository(application.applicationContext)

    private val _history = MutableLiveData<RequestValidation>()
    val history: LiveData<RequestValidation> = _history

    fun createHistory(title: String, gender: String) {
        repository.createNewHistory(StartModel(title, gender), object : APIListener<HistoryModel> {

            override fun onSuccess(result: HistoryModel) {
                _history.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _history.value = RequestValidation(message)
            }
        })
    }


}