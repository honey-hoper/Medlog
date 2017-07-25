package com.webhopers.medlog.accountCreationCode

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.webhopers.medlog.R
import kotlinx.android.synthetic.main.activity_account_creation_code.*

class AccountCreationCodeActivity : AccountCreationCodeView, AppCompatActivity() {

    lateinit var presenter: AccountCreationCodePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation_code)

        presenter = AccountCreationCodePresenter(this)
        acc_reset_btn.setOnClickListener { presenter.setACC() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    // AccountCreationCode View Functions
    override fun showProgressBar(bool: Boolean) {
        if (bool) acc_p_bar.visibility = View.VISIBLE
        else acc_p_bar.visibility = View.INVISIBLE
    }

    override fun setCode(code: Int) = acc_text_view.setText("$code")

    override fun getACCField() = acc_field
}
