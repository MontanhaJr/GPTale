package com.gptale.gptale.models

import com.google.gson.annotations.SerializedName

data class StartModel constructor(
    @SerializedName("title")
    var title: String,
    @SerializedName("gender")
    var gender: String
)