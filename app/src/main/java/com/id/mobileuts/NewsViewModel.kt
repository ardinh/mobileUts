package com.id.mobileuts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.id.mobileuts.models.News
import com.id.mobileuts.services.RetrofitClient
import com.id.mobileuts.services.NewsApiService
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val newsApiService = RetrofitClient.createService(NewsApiService::class.java)
    private val newses = mutableListOf<News>()

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    init {
        loadMoreNews("general")
    }

    fun loadMoreNews(category: String) {
        viewModelScope.launch {
            try {
                val response = newsApiService.getNews(category)
                if (response.isSuccessful) {
                    val news = response.body()?.articles ?: emptyList()
                    newses.addAll(news)
                    _newsList.value = news
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
