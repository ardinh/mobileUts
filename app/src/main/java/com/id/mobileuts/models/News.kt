package com.id.mobileuts.models

import com.google.gson.annotations.SerializedName

data class News(
    val author: String,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
)

