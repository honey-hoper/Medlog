package com.webhopers.medlog.login

import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.isEmpty
import com.webhopers.medlog.utils.isInternetAvailable

class LoginPresenter(val view: LoginView) {

    fun loginUser() {
        if (!validInput()) return

        if (!isInternetAvailable(view.getContext())) {
            view.showSnackbar(R.string.no_internet)
            return
        }

    }

    fun validInput(): Boolean {
        if (view.getEmailField().isEmpty()) {
            view.getEmailField().error = "Empty"
            return false
        }

        if (view.getPasswordField().isEmpty()) {
            view.getPasswordField().error = "Empty"
            return false
        }

        return true
    }

    fun createUserAccount() {
        view.startRegisterActivity()
    }
}
