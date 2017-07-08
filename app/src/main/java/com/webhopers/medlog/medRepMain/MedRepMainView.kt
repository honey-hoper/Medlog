package com.webhopers.medlog.medRepMain

import android.content.res.Resources
import android.support.v7.widget.RecyclerView

interface MedRepMainView {
    fun startSplashActivity()
    fun showProgressDialog(bool: Boolean)
    fun getRecyclerView(): RecyclerView
    fun showProgressBar(bool: Boolean)
}