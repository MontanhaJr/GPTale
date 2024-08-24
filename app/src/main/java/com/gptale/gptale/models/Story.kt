package com.gptale.gptale.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Story (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String,
    @SerializedName("gender")
    @ColumnInfo(name = "gender") val gender: String,
    @SerializedName("paragraph")
    @ColumnInfo(name = "paragraph") val paragraph: String,
    @SerializedName("paragraphCount")
    @ColumnInfo(name = "paragraphCount") val totalParagraphs: Int,
    @SerializedName("options")
    @ColumnInfo(name = "options") var options: List<String>,
    @SerializedName("fullStory")
    @ColumnInfo(name = "fullStory") var fullStory: String
) : Serializable