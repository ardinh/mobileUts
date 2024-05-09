package com.id.mobileuts

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

abstract class RecyclerWithTitleAndFooterAdapterContext<H : RecyclerWithTitleFooterViewHolder>(
    val activity: Activity,
    private val withCountItem: Boolean = true,
    private val withLoading: Boolean = true
) : RecyclerView.Adapter<H>() {

    abstract val layoutRes: Int

    abstract fun onBindView(holder: H, position: Int)

    abstract fun buildViewHolder(view: View, viewType: Int): H

    abstract fun getObjectCount(): Int

    val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    var totalData: Long = 0
        set(value) {
            field = value
            if (getItemViewType(0) == 1) {
                notifyItemChanged(0)
            }
        }

    var totalDataSuffix: String = "Item"

    private var progressView: ProgressBar? = null

    var isProgressVisible: Boolean = false
        set(value) {
            field = value
            progressView?.visibility = if (field) View.VISIBLE else View.GONE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val layoutResource = layoutRes

        val view = layoutInflater.inflate(layoutResource, parent, false)
        return buildViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        onBindView(holder, position)
    }

    override fun getItemCount(): Int = getObjectCount() + if (withCountItem) 2 else if (withLoading) 1 else 0

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> if (withCountItem) 1 else if (itemCount > 1) 0 else if (withLoading) 2 else 0
        (itemCount - 1) -> if (withLoading) 2 else 0
        else -> 0
    }

}

abstract class RecyclerWithTitleFooterViewHolder(
    itemView: View,
    val viewType: Int,
) : RecyclerView.ViewHolder(itemView) {


}