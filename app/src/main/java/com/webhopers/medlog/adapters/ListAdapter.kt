package com.webhopers.medlog.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import kotlinx.android.synthetic.main.manage_playlist_list_item.view.*

class ListAdapter(
        val dataset:List<String?> ):

        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = parent?.context?.inflateView(R.layout.manage_playlist_list_item)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.playlist_name.text = dataset[position]
    }

    override fun getItemCount() = dataset.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}