package com.webhopers.medlog.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.webhopers.medlog.adminMain.AdminMainActivity
import com.webhopers.medlog.login.LoginActivity
import com.webhopers.medlog.medRepMain.MedRepMainActivity
import com.webhopers.medlog.services.auth.FirebaseAuthService
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlin.properties.Delegates

class SplashActivity: AppCompatActivity() {

    private val SESSION_FILE = "SESSION_FILE"
    private val ADMIN_SESSION = "ADMIN_SESSION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) startActivity(Intent(this, LoginActivity::class.java))
        else if (user.isAnonymous) startActivity(Intent(this, AdminMainActivity::class.java))
        else startActivity(Intent(this, MedRepMainActivity::class.java))

        finish()
    }
}