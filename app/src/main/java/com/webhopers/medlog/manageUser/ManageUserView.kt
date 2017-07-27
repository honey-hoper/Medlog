package com.webhopers.medlog.manageUser

import android.content.Context

interface ManageUserView {
    fun showProgressBar(bool: Boolean)
    fun getContext(): Context
}