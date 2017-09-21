package com.webhopers.medlog.manageUser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import com.google.firebase.database.FirebaseDatabase

import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.FirebaseListAdapter
import kotlinx.android.synthetic.main.activity_manage_user.*

class ManageUserActivity : ManageUserView, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        setUpToolbar()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar_manage_user)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    // ManageUserView functions

    override fun showProgressBar(bool: Boolean) {
        if (bool) mng_usr_p_bar.visibility = View.VISIBLE
        else mng_usr_p_bar.visibility = View.INVISIBLE
    }

    override fun getContext() = this
}
