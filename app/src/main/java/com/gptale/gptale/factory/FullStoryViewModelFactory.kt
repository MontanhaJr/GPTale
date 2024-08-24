package com.gptale.gptale.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gptale.gptale.repository.FullStoryRepository
import com.gptale.gptale.viewmodels.FullStoryViewModel

class FullStoryViewModelFactory (
    private val repository: FullStoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FullStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FullStoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}