package com.webhopers.medlog.adminMain

import android.support.v7.widget.RecyclerView

interface AdminMainView {
    fun startSplashActivity()
    fun showProgressDialog(bool: Boolean)
    fun getRecyclerView(): RecyclerView

}