package com.webhopers.medlog.adapters

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso
import com.stfalcon.frescoimageviewer.ImageViewer
import com.webhopers.medlog.R
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.medRepMain.MedRepMainView
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerViewAdapterMR(val view: MedRepMainView,
                            val context: Context,
                            val resources: Resources,
                            query: Query?)
            : FirebaseRecyclerAdapter<ImageModel, RecyclerViewAdapterMR.ViewHolderMR>(ImageModel::class.java,
                R.layout.recycler_view_item,
                ViewHolderMR::class.java,
                query) {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderMR {
        val view = context.inflateView(R.layout.recycler_view_item)
        return ViewHolderMR(view, context, resources)
    }

    override fun populateViewHolder(viewHolder: ViewHolderMR?, model: ImageModel?, position: Int) {
        view.showProgressBar(false)
        viewHolder?.bindData(context, resources, model?.url, position)
    }


    class ViewHolderMR(itemView: View?,
                       val context: Context,
                       val resources: Resources)

        : RecyclerView.ViewHolder(itemView) {
        fun bindData(context: Context, r: Resources, url: String?, position: Int) {
            Picasso.with(context)
                    .load(url)
                    .resize(convertDpToPixels(100f, r).toInt(), convertDpToPixels(100f, r).toInt())
                    .centerCrop()
                    .into(itemView.recycler_view_image_view)

            itemView.setOnClickListener { startImageViewer(position) }
        }

        fun startImageViewer(position: Int) {
            val urls = DataHolder.Urls()
            if (urls == null)
                return

            ImageViewer.Builder(context, urls)
                    .setStartPosition(position)
                    .setImageMarginPx(convertDpToPixels(25f, resources).toInt())
                    .show()
            Fresco.initialize(context)

        }
    }


}