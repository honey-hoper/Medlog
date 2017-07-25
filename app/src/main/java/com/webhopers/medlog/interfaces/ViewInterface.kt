package com.webhopers.medlog.interfaces

import android.content.Context
import android.view.View

interface ViewInterface {
    fun showProgressBar(bool: Boolean)
    fun getRootLayout(): View
    fun getContext(): Context
    fun getDecorView(): View
}