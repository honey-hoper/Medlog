package com.webhopers.medlog.register

import android.widget.EditText

interface RegisterView {
    fun getNameField(): EditText
    fun getPhoneField(): EditText
    fun getEmailField(): EditText
    fun getPasswordField(): EditText
    fun getConfirmPasswordField(): EditText

}