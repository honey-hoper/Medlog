package com.webhopers.medlog.register

import android.content.Context
import android.widget.EditText

interface RegisterView {
    fun getContext(): Context

    fun getNameField(): EditText
    fun getPhoneField(): EditText
    fun getEmailField(): EditText
    fun getPasswordField(): EditText
    fun getConfirmPasswordField(): EditText
    fun getACCField(): EditText
    fun showSnackbar(id: Int)
    fun showProgressBar(bool: Boolean)
    fun enableButton(bool: Boolean)
    fun startMedRepActivity()

}