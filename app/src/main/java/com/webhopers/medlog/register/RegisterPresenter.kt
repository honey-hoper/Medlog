package com.webhopers.medlog.register

import com.webhopers.medlog.extensions.isEmpty
import com.webhopers.medlog.utils.isInternetAvailable
import java.util.regex.Pattern

class RegisterPresenter(val view: RegisterView) {

    fun registerUser() {
        if (!validInput()) return

        if (!isInternetAvailable(view.getContext())) {
            view.showSnackbar("Internet not available")
            return
        }

    }

    private fun validInput(): Boolean {
        if (view.getNameField().isEmpty()) {
            view.getNameField().error = "Empty"
            return false
        }

        if (view.getPhoneField().isEmpty()) {
            view.getPhoneField().error = "Empty"
            return false
        }

        if (view.getEmailField().isEmpty()) {
            view.getEmailField().error = "Empty"
            return false
        }

        if (view.getPasswordField().isEmpty()) {
            view.getPasswordField().error = "Empty"
            return false
        }

        if (view.getConfirmPasswordField().isEmpty()) {
            view.getConfirmPasswordField().error = "Empty"
            return false
        }

        if (view.getPhoneField().text.toString().length != 10) {
            view.getPhoneField().error = "Enter correct Phone number"
            return false
        }

        if (!isEmailFormatCorrect()) {
            view.getEmailField().error = "Enter correct Email address"
            return false
        }

        if (view.getPasswordField().text.toString() != view.getConfirmPasswordField().text.toString()) {
            view.getConfirmPasswordField().error = "Password does not match"
            return false
        }
        return true;
    }

    private fun isEmailFormatCorrect(): Boolean {
        val matcher = Pattern.compile("\\w+@\\w+.\\w+").matcher(view.getEmailField().text.toString())
        return matcher.find()
    }

}