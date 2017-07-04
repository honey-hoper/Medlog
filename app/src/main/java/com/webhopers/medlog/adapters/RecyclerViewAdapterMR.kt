package com.webhopers.medlog.adapters

import android.content.Context
import android.content.res.Resources
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.viewHolder.ViewHolderMR

class RecyclerViewAdapterMR(val context: Context,
                            val resources: Resources,
                            query: Query?) :
        FirebaseRecyclerAdapter<String, ViewHolderMR>(String::class.java,
                R.layout.recycler_view_item,
                ViewHolderMR::class.java,
                query) {

    override fun populateViewHolder(viewHolder: ViewHolderMR?, model: String?, position: Int) {
        viewHolder?.bindData(context, resources, model)
    }
}