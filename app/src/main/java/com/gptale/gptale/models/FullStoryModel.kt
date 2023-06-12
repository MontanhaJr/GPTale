package com.gptale.gptale.models

import com.google.gson.annotations.SerializedName

data class FullStoryModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("fullStory")
    var fullStory: String
)
