package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gptale.gptale.constants.Constants.STORY.MAX_PARAGRAPH
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.models.RequestValidation
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.repository.StartRepository
import com.gptale.gptale.retrofit.APIListener

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StartRepository(application.applicationContext)

    private val _storyRequest = MutableLiveData<RequestValidation>()
    val storyRequest: LiveData<RequestValidation> = _storyRequest

    var story: StoryModel? = null

    fun createStory(title: String, gender: String) {
        repository.createNewStory(StartModel(title, gender, MAX_PARAGRAPH), object : APIListener<StoryModel> {

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