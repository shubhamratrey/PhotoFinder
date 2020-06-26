package com.zoopzam.photofinder.views.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoopzam.photofinder.R
import com.zoopzam.photofinder.constants.Constants
import com.zoopzam.photofinder.constants.Constants.HOME_PAGINATE
import com.zoopzam.photofinder.constants.Constants.HOME_SCROLL
import com.zoopzam.photofinder.models.HomeItem
import com.zoopzam.photofinder.models.responses.HomeDataResponse
import com.zoopzam.photofinder.utils.ImageManager
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home.*

class HomeAdapter(val context: Context, val response: HomeDataResponse, val listener: (Any, Int, String) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val commonItemLists = ArrayList<Any>()
    var pageNo = 0
    var scrollBackPosition: Int = 6
    var TAG = HomeAdapter::class.java.simpleName


    companion object {
        const val PROGRESS_VIEW = 0
        const val HOME_ITEM = 1
        const val SCROLLBACK_SHOW_ID = -111
        const val SCROLLBACK_HIDE_ID = -222
    }

    init {
        if (response.items != null && response.items!!.isNotEmpty()) {
            pageNo++
            commonItemLists.addAll(response.items!!)
            if (response.hasMore != null && response.hasMore!!) {
                commonItemLists.add(PROGRESS_VIEW)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (commonItemLists[position] is HomeItem) {
            HOME_ITEM
        } else {
            PROGRESS_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            HOME_ITEM -> LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
            PROGRESS_VIEW -> LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
            else -> LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commonItemLists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HOME_ITEM -> {
                setHomeData(holder, position)
            }
        }
        if (holder.adapterPosition == itemCount - 1) {
            if (response.hasMore != null && response.hasMore!!) {
                listener(pageNo, -1, HOME_PAGINATE)
            }

            if (position > scrollBackPosition) {
                // show scroll back visible
                listener(SCROLLBACK_SHOW_ID, -1, HOME_SCROLL)
            } else {
                // hide scroll back visible
                listener(SCROLLBACK_HIDE_ID, -1, HOME_SCROLL)
            }
        }
    }

    private fun setHomeData(holder: ViewHolder, position: Int) {
        val item = commonItemLists[holder.adapterPosition] as HomeItem
        if (item.label != null) {
            holder.labelTv.text = item.label
        }
        holder.image.setImageDrawable(null)
        if (item.itemType?.equals(Constants.HOME_ITEM_TYPE.IMAGE)!! && item.photoUrl != null){
            ImageManager.loadImage(holder.image, item.photoUrl)
        } else {
            holder.image.setImageResource(R.drawable.ic_baseline_folder_24)
        }


        holder.containerView.setOnClickListener {
            listener(item, holder.adapterPosition, "")
        }
    }

    fun addMoreData(response: HomeDataResponse?) {
        val oldSize = itemCount
        commonItemLists.remove(PROGRESS_VIEW)
        if (response != null && response.items!!.isNotEmpty()) {
            pageNo++
            this.response.items!!.addAll(response.items!!)
            this.response.hasMore = response.hasMore
            commonItemLists.addAll(response.items!!)
        }
        if (response?.hasMore!!) {
            commonItemLists.add(PROGRESS_VIEW)
        }
        notifyItemRangeChanged(oldSize, itemCount)
    }

    fun clearData() {
        commonItemLists.clear()
    }

    class ItemDecoration(private val leftMargin: Int, private val topMargin: Int, private val rightMargin: Int, private val verticalSpaceHeight: Int, private val lastItemSpace: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = topMargin
            }
            outRect.left = leftMargin
            outRect.right = rightMargin

            if (lastItemSpace != 0 && parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
                outRect.bottom = lastItemSpace
            } else {
                outRect.bottom = verticalSpaceHeight
            }
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}