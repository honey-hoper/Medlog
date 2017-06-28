package com.webhopers.medlog.medRepMain.recyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView


class RecyclerViewAdapter(val context: Context): RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        val view = context.inflateView(R.layout.recycler_view_item)
        val viewHolder = RecyclerViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 24
    }


}
class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
}