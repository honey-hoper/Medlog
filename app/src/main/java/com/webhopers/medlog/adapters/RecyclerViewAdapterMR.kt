package com.webhopers.medlog.adapters

import android.content.Context
import android.content.res.Resources
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.viewHolder.ViewHolderMR
import com.webhopers.medlog.models.ImageModel

class RecyclerViewAdapterMR(val context: Context,
                            val resources: Resources,
                            query: Query?) :
        FirebaseRecyclerAdapter<ImageModel, ViewHolderMR>(ImageModel::class.java,
                R.layout.recycler_view_item,
                ViewHolderMR::class.java,
                query) {

    override fun populateViewHolder(viewHolder: ViewHolderMR?, model: ImageModel?, position: Int) {
        viewHolder?.bindData(context, resources, model?.url)
    }
}