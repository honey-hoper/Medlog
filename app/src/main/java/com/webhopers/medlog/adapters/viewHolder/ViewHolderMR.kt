package com.webhopers.medlog.adapters.viewHolder

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class ViewHolderMR(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bindData(context: Context, r: Resources, url: String?) {
        Picasso.with(context)
                .load(url)
                .resize(convertDpToPixels(100f, r).toInt(), convertDpToPixels(100f, r).toInt())
                .centerCrop()
                .into(itemView.recycler_view_image_view)
    }
}