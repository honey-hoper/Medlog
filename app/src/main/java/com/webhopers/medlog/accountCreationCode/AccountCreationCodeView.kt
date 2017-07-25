package com.webhopers.medlog.accountCreationCode

import android.support.design.widget.TextInputEditText

interface AccountCreationCodeView {

    fun showProgressBar(bool: Boolean)
    fun setCode(code: Int)
    fun getACCField(): TextInputEditText
}