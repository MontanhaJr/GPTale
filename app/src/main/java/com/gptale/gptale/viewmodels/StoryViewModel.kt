package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.OptionModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.repository.StoryRepository
import com.gptale.gptale.retrofit.APIListener

class StoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StoryRepository(application.applicationContext)

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    var story: StoryModel? = null

    fun sendOption(idStory: Int, optionSelected: Int) {
        repository.sendOptionSelected(OptionModel(idStory, optionSelected), object : APIListener<StoryModel> {

            override fun onSuccess(result: StoryModel) {
                story = result
                _storyRequest.value = RequestValidation()
            }

            override fun onFailure(message: String) {
                _storyRequest.value = RequestValidation(message)
            }
        })
    }


}