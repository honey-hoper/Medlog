package com.webhopers.medlog.login

import android.content.Context
import android.widget.EditText

interface LoginView {
    fun getContext(): Context

    fun getEmailField(): EditText
    fun getPasswordField(): EditText
    fun showSnackbar(id: Int)
    fun startRegisterActivity()
}