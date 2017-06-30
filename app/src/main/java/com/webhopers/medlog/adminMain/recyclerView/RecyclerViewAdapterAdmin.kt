package com.webhopers.medlog.adminMain.recyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import java.util.*

class RecyclerViewAdapterAdmin(val context: Context): RecyclerView.Adapter<RecyclerViewHolderAdmin>() {

    val arr = intArrayOf(R.drawable.med1, R.drawable.med2, R.drawable.med3)

    override fun onBindViewHolder(holder: RecyclerViewHolderAdmin?, position: Int) {
        val viewType = holder?.itemViewType

        if (viewType == 1) addImage(holder?.itemView?.image_view)


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolderAdmin {
        if (viewType == 0)
            return RecyclerViewHolderAdmin.ButtonHolder(createButtonHolder())
        else
            return RecyclerViewHolderAdmin.ImageHolder(createImageHolder())
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        else return 1
    }

    private fun createImageHolder(): View {
        println("Image View Created")
        return context.inflateView(R.layout.recycler_view_item)
    }

    private fun addImage(imageView: ImageView?) {
        val random = Random()
        imageView?.setImageResource(arr[Math.abs(random.nextInt() % 3)])
    }

    private fun createButtonHolder(): View {
        println("Image Button Created")
        return context.inflateView(R.layout.recycler_view_item_button)
    }




}

sealed class RecyclerViewHolderAdmin(itemView: View?) : RecyclerView.ViewHolder(itemView){
    class ImageHolder(itemView: View?) : RecyclerViewHolderAdmin(itemView)
    class ButtonHolder(itemView: View?) : RecyclerViewHolderAdmin(itemView)
}