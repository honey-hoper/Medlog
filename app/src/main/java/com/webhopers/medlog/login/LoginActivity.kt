package com.webhopers.medlog.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View

import com.webhopers.medlog.R
import com.webhopers.medlog.adminMain.AdminMainActivity
import com.webhopers.medlog.medRepMain.MedRepMainActivity
import com.webhopers.medlog.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : LoginView, AppCompatActivity() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this, resources)

        login_button.setOnClickListener { presenter.onLogin() }
        create_acc_button.setOnClickListener { presenter.createUserAccount() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
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

    override fun showProgressBar(bool: Boolean) {
        progress_bar_login.visibility = if (bool) View.VISIBLE else View.GONE
    }

    override fun enableButtons(bool: Boolean) {
        login_button.isEnabled = bool
        create_acc_button.isEnabled = bool
    }

    override fun startMedRepActivity() {
        startActivity(Intent(this, MedRepMainActivity::class.java))
        finish()
    }

    override fun startAdminMainActivity() {
        startActivity(Intent(this, AdminMainActivity::class.java))
        finish()
    }

}
