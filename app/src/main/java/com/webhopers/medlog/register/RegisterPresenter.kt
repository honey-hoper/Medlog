package com.webhopers.medlog.register

import com.webhopers.medlog.extensions.isEmpty
import java.util.regex.Pattern

class RegisterPresenter(val view: RegisterView) {

    fun registerUser() {
        validateInput()
    }

    private fun validateInput() {
        if (view.getNameField().isEmpty()) {
            view.getNameField().error = "Empty"
            return
        }

        if (view.getPhoneField().isEmpty()) {
            view.getPhoneField().error = "Empty"
            return
        }

        if (view.getEmailField().isEmpty()) {
            view.getEmailField().error = "Empty"
            return
        }

        if (view.getPasswordField().isEmpty()) {
            view.getPasswordField().error = "Empty"
            return
        }

        if (view.getConfirmPasswordField().isEmpty()) {
            view.getConfirmPasswordField().error = "Empty"
            return
        }

        if (view.getPhoneField().text.toString().length != 10) {
            view.getPhoneField().error = "Enter correct Phone number"
            return
        }

        if (!isEmailFormatCorrect()) {
            view.getEmailField().error = "Enter correct Email address"
            return
        }

        if (view.getPasswordField().text.toString() != view.getConfirmPasswordField().text.toString()) {
            view.getConfirmPasswordField().error = "Password does not match"
            return
        }
    }

    private fun isEmailFormatCorrect(): Boolean {
        val matcher = Pattern.compile("\\w+@\\w+.\\w+").matcher(view.getEmailField().text.toString())
        return matcher.find()
    }
}