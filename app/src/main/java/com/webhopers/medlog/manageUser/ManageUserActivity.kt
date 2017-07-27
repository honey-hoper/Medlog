package com.webhopers.medlog.manageUser

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.firebase.database.FirebaseDatabase

import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.FirebaseListAdapter
import kotlinx.android.synthetic.main.activity_manage_user.*

class ManageUserActivity : ManageUserView, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        showProgressBar(true)

        mng_usr_rec_view.adapter = FirebaseListAdapter(this, FirebaseDatabase
                .getInstance()
                .getReference("users"))

        mng_usr_rec_view.layoutManager = LinearLayoutManager(this)

        mng_usr_rec_view.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider))
        mng_usr_rec_view.addItemDecoration(itemDecoration)
    }

    // ManageUserView functions

    override fun showProgressBar(bool: Boolean) {
        if (bool) mng_usr_p_bar.visibility = View.VISIBLE
        else mng_usr_p_bar.visibility = View.INVISIBLE
    }

    override fun getContext() = this
}
