package com.gptale.gptale.models

data class HistoryModel constructor(
    var id: Int,
    var title: String,
    var gender: String,
    var paragraph: String,
    var options: List<String>,
    var fullHistory: String?
)