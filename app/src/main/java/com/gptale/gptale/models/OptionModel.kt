package com.gptale.gptale.models

import com.google.gson.annotations.SerializedName

data class OptionModel constructor(
    @SerializedName("id")
    var id: Int,
    @SerializedName("option")
    var option: Int
)