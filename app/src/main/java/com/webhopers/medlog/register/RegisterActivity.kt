package com.webhopers.medlog.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.webhopers.medlog.R
import com.webhopers.medlog.medRepMain.MedRepMainActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : RegisterView, AppCompatActivity() {

    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)
        register_button.setOnClickListener { presenter.onRegister() }
    }

    override fun onDestroy() = presenter.unsubscribe()

    //Register View Functions

    override fun getContext() = this

    override fun getNameField() = name_field

    override fun getPhoneField() = phone_field

    override fun getEmailField() = email_field

    override fun getPasswordField() = password_field

    override fun getConfirmPasswordField() = confirm_password_field

    override fun showSnackbar(id: Int) = Snackbar.make(register_root_layout, id, Snackbar.LENGTH_SHORT).show()

    override fun showProgressBar(bool: Boolean) {
        progress_bar_register.visibility = if (bool) View.VISIBLE else View.INVISIBLE
    }

    override fun enableButton(bool: Boolean) {
        register_button.isEnabled = bool
    }

    override fun startMedRepActivity() {
        startActivity(Intent(this, MedRepMainActivity::class.java))
        finish()
    }


}
