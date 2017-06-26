package com.webhopers.medlog.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.isEmpty
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : RegisterView, AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val presenter = RegisterPresenter(this)
        register_button.setOnClickListener { presenter.registerUser() }
    }

    //Register View Functions

    override fun getNameField() = name_field

    override fun getPhoneField() = phone_field

    override fun getEmailField() = email_field

    override fun getPasswordField() = password_field

    override fun getConfirmPasswordField() = confirm_password_field
}
