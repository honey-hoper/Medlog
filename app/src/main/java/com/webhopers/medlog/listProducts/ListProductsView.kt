package com.webhopers.medlog.listProducts

import android.support.v7.widget.RecyclerView

interface ListProductsView {

    fun showProgressBar(bool: Boolean)
    fun makeToast(message: String)
    fun getRecyclerView(): RecyclerView
}