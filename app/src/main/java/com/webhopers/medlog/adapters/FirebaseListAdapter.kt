package com.webhopers.medlog.adapters


import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.webhopers.medlog.R
import com.webhopers.medlog.manageUser.ManageUserView
import com.webhopers.medlog.models.MedRepInfo
import kotlinx.android.synthetic.main.manage_user_list_item.view.*

class FirebaseListAdapter(
        val view: ManageUserView,
        query: Query?) : FirebaseRecyclerAdapter<MedRepInfo, ListAdapter.ViewHolder>(
        MedRepInfo::class.java,
        R.layout.manage_user_list_item,
        ListAdapter.ViewHolder::class.java,
        query) {


    override fun populateViewHolder(viewHolder: ListAdapter.ViewHolder, model: MedRepInfo, position: Int) {
        view.showProgressBar(false)
        viewHolder.itemView.apply {
            user_name_view.text = model.name
            user_email_view.text = model.email
            user_number_view.text = model.phone
        }
    }

}