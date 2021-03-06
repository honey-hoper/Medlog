package com.webhopers.medlog.login

import android.content.Context
import android.widget.EditText

interface LoginView {
    fun getContext(): Context

    fun getEmailField(): EditText
    fun getPasswordField(): EditText
    fun showSnackbar(id: Int)
    fun startRegisterActivity()
    fun showProgressBar(bool: Boolean)
    fun enableButtons(bool: Boolean)
    fun startMedRepActivity()
    fun startAdminMainActivity()
    fun showInvalidLoginView(bool: Boolean)
}