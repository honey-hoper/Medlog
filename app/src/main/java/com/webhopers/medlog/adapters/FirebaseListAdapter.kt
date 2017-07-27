package com.webhopers.medlog.adapters


import android.support.v7.widget.RecyclerView
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.webhopers.medlog.R
import com.webhopers.medlog.dialogs.ConfirmationDialog
import com.webhopers.medlog.manageUser.ManageUserView
import com.webhopers.medlog.models.MedRepInfo
import kotlinx.android.synthetic.main.manage_user_list_item.view.*

class FirebaseListAdapter(
        val view: ManageUserView,
        query: Query?) : FirebaseRecyclerAdapter<MedRepInfo, FirebaseListAdapter.ViewHolder>(
        MedRepInfo::class.java,
        R.layout.manage_user_list_item,
        ViewHolder::class.java,
        query) {


    override fun populateViewHolder(viewHolder: ViewHolder, model: MedRepInfo, position: Int) {
        view.showProgressBar(false)
        viewHolder.createView(model)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun createView(model: MedRepInfo) {
            itemView.apply {
                user_name_view.text = model.name
                user_email_view.text = model.email
                user_number_view.text = model.phone
                cross.setOnClickListener { ConfirmationDialog(itemView.context, model.uid!!) }
            }
        }
    }

}