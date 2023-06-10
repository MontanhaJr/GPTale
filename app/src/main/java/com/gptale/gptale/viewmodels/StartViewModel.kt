package com.gptale.gptale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gptale.gptale.models.StartModel
import com.gptale.gptale.repository.StartRepository

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StartRepository()

    fun createHistory(title: String, gender: String) {
        repository.createNewHistory(StartModel(title, gender))
    }


}