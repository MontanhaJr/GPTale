package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.constants.Constants.HISTORY.MAX_PARAGRAPH
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.repository.StartRepository
import com.gptale.gptale.retrofit.APIListener

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StartRepository(application.applicationContext)

    private val _historyRequest = MutableLiveData<RequestValidation>()
    val historyRequest: LiveData<RequestValidation> = _historyRequest

    var history: StoryModel? = null

    fun createHistory(title: String, gender: String) {
        repository.createNewHistory(StartModel(title, gender, MAX_PARAGRAPH), object : APIListener<StoryModel> {

            override fun onSuccess(result: StoryModel) {
                history = result
                _historyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _historyRequest.value = RequestValidation(message)
            }
        })
    }


}