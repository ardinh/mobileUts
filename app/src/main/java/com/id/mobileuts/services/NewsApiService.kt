package com.id.mobileuts.services

import com.id.mobileuts.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines?country=us&apiKey=6fa1801724f34497a5fee442e7b93f73")
    suspend fun getNews(
        @Query("category") category: String
    ): Response<NewsResponse>
}
