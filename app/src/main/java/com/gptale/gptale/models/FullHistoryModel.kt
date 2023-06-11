package com.gptale.gptale.models

import com.google.gson.annotations.SerializedName

data class FullHistoryModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("fullHistory")
    var fullHistory: String
)
