package com.id.mobileuts

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.id.mobileuts.databinding.ItemNewsBinding
import com.id.mobileuts.models.News
import com.id.mobileuts.utils.util
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(
    context: Activity,
    private val news: List<News>,
    val onClickItem: (News) -> Unit) :
    RecyclerWithTitleAndFooterAdapterContext<NewsAdapter.NewsAdapterVH>(
        context,
        withCountItem = false,
        withLoading = false
    ) {

    inner class NewsAdapterVH(
        view: View,
        viewType: Int
    ) : RecyclerWithTitleFooterViewHolder(view, viewType) {
        fun setContent(context: Context, data: News) {
            ItemNewsBinding.bind(itemView).apply {
                val date = stringToCalendar(
                    data.publishedAt,
                    util.DATE_PATTERN_FROM_SERVER
                )
                root.setOnClickListener {
                    onClickItem(data)
                }
                if(data.urlToImage != null){
                    Glide.with(context)
                        .load(data.urlToImage)
                        .placeholder(R.drawable.ic_news_paper)
                        .centerCrop()
                        .into(imgIcon)
                }
                tvTitle.text = data.title
                tvAuthor.text = data.author
                tvDate.text = util.dateToShortStringWithTime(date!!)
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.item_news

    override fun onBindView(holder: NewsAdapterVH, position: Int) {
        holder.setContent(holder.itemView.context,news[position])
    }

    override fun buildViewHolder(view: View, viewType: Int): NewsAdapterVH =
        NewsAdapterVH(view, viewType)

    override fun getObjectCount(): Int = news.size

    fun stringToCalendar(input: String, pattern: String): Calendar? {
        return try {
            val date = SimpleDateFormat(pattern, Locale.US)
            date.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            val newDate = date.parse(input)

            val cal = Calendar.getInstance()
            cal.time = newDate!!
            cal
        } catch (e: ParseException) {
            Log.e("NewsAdapter", e.message ?: "Parsing date failed")
            null
        }
    }
}
