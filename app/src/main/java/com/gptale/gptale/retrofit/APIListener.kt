package com.gptale.gptale.retrofit

interface APIListener<T> {
    fun onSuccess(result: T)
    fun onFailure(message: String)
}