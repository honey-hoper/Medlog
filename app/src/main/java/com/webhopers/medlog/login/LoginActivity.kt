package com.webhopers.medlog.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar

import com.webhopers.medlog.R
import com.webhopers.medlog.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : LoginView, AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val presenter = LoginPresenter(this)

        login_button.setOnClickListener { presenter.loginUser() }
        create_acc_button.setOnClickListener { presenter.createUserAccount() }
    }

    //Login View Functions

    override fun getContext() = this

    override fun getEmailField() = email_field

    override fun getPasswordField() = password_field

    override fun showSnackbar(id: Int) = Snackbar.make(login_root_layout, id, Snackbar.LENGTH_SHORT).show()

    override fun startRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
