package com.gptale.gptale.retrofit

interface APIListener {
    fun onSuccess(model: Any)
    fun onFailure(str: String)
}