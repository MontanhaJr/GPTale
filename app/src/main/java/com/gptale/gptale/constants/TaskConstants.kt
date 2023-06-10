package com.gptale.gptale.constants

class TaskConstants private constructor(){

    object HTTP {
        const val SUCCESS = 200
        const val CREATED = 201
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }
}