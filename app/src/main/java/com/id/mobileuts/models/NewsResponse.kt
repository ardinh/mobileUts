package com.id.mobileuts.models

data class NewsResponse(
    val status: String,
    val totalResult: Int,
    val articles: List<News>
)

