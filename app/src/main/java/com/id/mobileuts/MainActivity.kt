package com.id.mobileuts

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.id.mobileuts.databinding.ActivityMainBinding
import com.id.mobileuts.models.News
import com.id.mobileuts.services.SPManager


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    private val listNews = mutableListOf<News>()

    private var isGeneral = false
    private var isBusiness = false
    private var isTech = false
    private var isSport = false

    private val _spManager: SPManager by lazy {
        SPManager(applicationContext)
    }
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        binding.toolbar.let { toolbar ->
            toolbar.pageTitle.text = getString(R.string.title)
            toolbar.pageTitle.setTextColor(
                ContextCompat.getColor(this, R.color.white)
            )
            toolbar.toolbarAdditionalAction.isVisible = true
            toolbar.toolbarAdditionalAction.setImageResource(R.drawable.ic_logout)
            toolbar.toolbarAdditionalAction.setOnClickListener {
                _spManager.putBoolean("logged", false)
            }
        }

        adapter = NewsAdapter(this,listNews){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(it.url))
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.loadMoreNews("general")
        isGeneral = true
        setChipState(binding.chipGeneral,isGeneral)

        binding.chipGeneral.setOnClickListener {
            isSport = false
            isTech = false
            isBusiness = false
            isGeneral = true

            setChipState(binding.chipGeneral,isGeneral)
            setChipState(binding.chipTech,isTech)
            setChipState(binding.chipBusiness,isBusiness)
            setChipState(binding.chipSport,isSport)
            viewModel.loadMoreNews("general")
        }

        binding.chipBusiness.setOnClickListener {
            isSport = false
            isTech = false
            isBusiness = true
            isGeneral = false

            setChipState(binding.chipGeneral,isGeneral)
            setChipState(binding.chipTech,isTech)
            setChipState(binding.chipBusiness,isBusiness)
            setChipState(binding.chipSport,isSport)
            viewModel.loadMoreNews("business")
        }

        binding.chipTech.setOnClickListener {
            isSport = false
            isTech = true
            isBusiness = false
            isGeneral = false

            setChipState(binding.chipGeneral,isGeneral)
            setChipState(binding.chipTech,isTech)
            setChipState(binding.chipBusiness,isBusiness)
            setChipState(binding.chipSport,isSport)
            viewModel.loadMoreNews("technology")
        }

        binding.chipSport.setOnClickListener {
            isSport = true
            isTech = false
            isBusiness = false
            isGeneral = false

            setChipState(binding.chipGeneral,isGeneral)
            setChipState(binding.chipTech,isTech)
            setChipState(binding.chipBusiness,isBusiness)
            setChipState(binding.chipSport,isSport)
            viewModel.loadMoreNews("sports")
        }
        setupViewModel()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setupViewModel(){
        viewModel.newsList.observe(this) {
            if(it != null) {
                listNews.clear()
                listNews.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun setChipState(chip: Chip, checked: Boolean) {
        chip.isEnabled = true
        chip.isChecked = checked
        if (checked) {
            chip.setChipBackgroundColorResource(R.color.blue_80)
            chip.setChipStrokeColorResource(R.color.blue_80)
            chip.setTextColor(applicationContext.getColor(R.color.white))
            chip.setChipIconTintResource(R.color.white)
        } else {
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setChipStrokeColorResource(R.color.grey_60)
            chip.setTextColor(applicationContext.getColor(R.color.grey_80))
            chip.setChipIconTintResource(R.color.grey_80)
        }
    }
}
