package com.webhopers.medlog.medRepMain

import android.content.Context
import com.webhopers.medlog.services.auth.FirebaseAuthService

class MedRepMainPresenter(val view: MedRepMainView, val context: Context) {

    fun signout() {
        view.showProgressDialog(true)
        FirebaseAuthService.signOutUser()
        view.showProgressDialog(false)
        view.startSplashActivity()
    }
}
