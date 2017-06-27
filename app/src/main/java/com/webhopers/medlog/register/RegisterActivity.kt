package com.webhopers.medlog.register

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
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

    override fun getContext() = this

    override fun getNameField() = name_field

    override fun getPhoneField() = phone_field

    override fun getEmailField() = email_field

    override fun getPasswordField() = password_field

    override fun getConfirmPasswordField() = confirm_password_field

    override fun showSnackbar(id: Int) = Snackbar.make(register_root_layout, id, Snackbar.LENGTH_SHORT).show()

}
