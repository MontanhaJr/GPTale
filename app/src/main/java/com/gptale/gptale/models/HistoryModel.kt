package com.gptale.gptale.models

import com.google.gson.annotations.SerializedName

data class HistoryModel constructor(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("paragraph")
    var paragraph: String,
    @SerializedName("options")
    var options: List<String>,
    @SerializedName("fullHistory")
    var fullHistory: String?
)