package com.webhopers.medlog.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.services.database.RealmDatabaseService
import kotlinx.android.synthetic.main.manage_playlist_list_item.view.*

class ListAdapter(val dataset:MutableList<String?> ): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            val position = viewHolder!!.adapterPosition
            removeItem(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = parent?.context?.inflateView(R.layout.manage_playlist_list_item)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.playlist_name.text = dataset[position]
    }

    override fun getItemCount() = dataset.size

    fun removeItem(position: Int) {
        RealmDatabaseService.deletePlaylist(dataset[position]!!)
        dataset.removeAt(position)
        notifyItemRemoved(position)

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}