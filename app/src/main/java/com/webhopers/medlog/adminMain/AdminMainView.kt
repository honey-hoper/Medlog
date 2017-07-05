package com.webhopers.medlog.adminMain

import android.app.Activity
import android.app.ProgressDialog
import android.support.v7.widget.RecyclerView

interface AdminMainView {
    fun startSplashActivity()
    fun showProgressDialog(bool: Boolean)
    fun setProgressDialogStyle(style: Int = ProgressDialog.STYLE_SPINNER,
                               message: String = "wait",
                               indeterminate: Boolean = true)
    fun setProgressDialogProgress(progress: Int)
    fun getRecyclerView(): RecyclerView
    fun makeToast(message: String)

}