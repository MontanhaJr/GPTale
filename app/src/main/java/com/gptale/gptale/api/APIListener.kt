package com.gptale.gptale.api

interface APIListener<T> {
    fun onSuccess(result: T)
    fun onFailure(message: String)
}