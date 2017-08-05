package com.webhopers.medlog.medRepMain

import android.content.Intent
import android.content.res.Resources
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.webhopers.medlog.interfaces.ViewInterface

interface MedRepMainView : ViewInterface{
    fun startSplashActivity()
    fun showProgressDialog(bool: Boolean)
    fun getRecyclerView(): RecyclerView
    fun startIntentActivity(intent: Intent)
    fun makeToast(message: String)
    fun startManagePlaylistActivity()
    fun startListProductsActivity()
}