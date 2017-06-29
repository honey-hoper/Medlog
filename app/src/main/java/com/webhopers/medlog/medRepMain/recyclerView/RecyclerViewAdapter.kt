package com.webhopers.medlog.medRepMain.recyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import java.util.*


class RecyclerViewAdapter(val context: Context): RecyclerView.Adapter<RecyclerViewHolder>() {

    val arr = intArrayOf(R.drawable.med1, R.drawable.med2, R.drawable.med3)

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        Picasso.with(context)
                .load(arr[Math.abs(Random().nextInt() % 3)])
                .resize(convertDpToPixels(100f, context.resources).toInt(), convertDpToPixels(100f, context.resources).toInt())
                .centerCrop()
                .into(holder!!.itemView.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        val view = context.inflateView(R.layout.recycler_view_item)
        val viewHolder = RecyclerViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 1600
    }


}
class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
}