package com.id.mobileuts.services

import com.id.mobileuts.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
    @GET("top-headlines?country=id&apiKey=6fa1801724f34497a5fee442e7b93f73")
    suspend fun getNews(): Response<NewsResponse>
}
