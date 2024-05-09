package com.id.mobileuts

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.id.mobileuts.databinding.ActivityMainBinding
import com.id.mobileuts.models.News


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    private val listNews = mutableListOf<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        adapter = NewsAdapter(this,listNews){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(it.url))
            startActivity(intent)
        }


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.loadMoreNews()
        setupViewModel()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setupViewModel(){
        viewModel.newsList.observe(this) {
            if(it != null) {
                listNews.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
