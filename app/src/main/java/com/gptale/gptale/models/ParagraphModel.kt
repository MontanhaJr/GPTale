package com.gptale.gptale.models

data class ParagraphModel (
    val paragraph: String,
    val options: List<String>,
    val question: String?
)