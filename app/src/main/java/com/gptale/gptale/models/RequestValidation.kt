package com.gptale.gptale.models

class RequestValidation(message: String = "") {

    private var status: Boolean = true
    private var validationMessage: String = ""

    init {
        if (message != "") {
            this.status = false
            this.validationMessage = message
        }
    }

    fun status() = status
    fun message() = validationMessage
}