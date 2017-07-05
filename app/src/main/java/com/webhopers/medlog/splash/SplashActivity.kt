package com.webhopers.medlog.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.webhopers.medlog.adminMain.AdminMainActivity
import com.webhopers.medlog.login.LoginActivity
import com.webhopers.medlog.medRepMain.MedRepMainActivity
import com.webhopers.medlog.services.auth.FirebaseAuthService

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        if (!FirebaseAuthService.isSessionActive())
            startActivity(Intent(this, LoginActivity::class.java))
        else
            startActivity(Intent(this, MedRepMainActivity::class.java))
        finish()
    }
}