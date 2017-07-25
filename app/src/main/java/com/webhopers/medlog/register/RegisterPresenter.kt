package com.webhopers.medlog.register

import android.widget.Toast
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.isEmpty
import com.webhopers.medlog.models.MedRepInfo
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import com.webhopers.medlog.utils.isConnected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class RegisterPresenter(val view: RegisterView,
                        val disposable: CompositeDisposable = CompositeDisposable()) {

    var ACC: Int? = null

    init {
        getACC()
    }

    fun getACC() {
        disposable.add(FirebaseDatabaseService.getACC()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {},
                        onComplete = {},
                        onNext = {ACC = it}))
    }

    fun onRegister() {
        if (!validInput()) return

        if (!isConnected(view.getContext())) {
            view.showSnackbar(R.string.no_internet)
            return
        }

        val name = view.getNameField().text.toString().trim()
        val email = view.getEmailField().text.toString().trim()
        val phone = view.getPhoneField().text.toString().trim()
        val uid = ""
        val password = view.getPasswordField().text.toString().trim()
        val acc = view.getACCField().text.toString().trim().toInt()

        if (ACC == null) {
            Toast.makeText(view.getContext(), "Network Error", Toast.LENGTH_SHORT).show()
            return
        }

        if (acc != ACC) {
            view.getACCField().error = "Wrong ACC"
            return
        }

        val mr = MedRepInfo(uid, name, phone, email)

        view.showProgressBar(true)
        view.enableButton(false)
        createUser(mr, password)
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

        if (view.getPasswordField().text.toString().length < 6) {
            view.getPasswordField().setError("Minimum length must be 6")
            return false
        }

        if (view.getConfirmPasswordField().isEmpty()) {
            view.getConfirmPasswordField().error = "Empty"
            return false
        }

        if (view.getACCField().isEmpty()) {
            view.getACCField().error = "Empty"
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
        return true
    }

    private fun isEmailFormatCorrect(): Boolean {
        val matcher = Pattern.compile("\\w+@\\w+.\\w+").matcher(view.getEmailField().text.toString())
        return matcher.find()
    }

    private fun createUser(mr: MedRepInfo, password: String) {
        disposable.add(FirebaseAuthService.createUser(mr.email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            mr.uid = FirebaseAuthService.getUID()
                            FirebaseDatabaseService.addUser(mr)
                            view.startMedRepActivity()
                        },
                        onError = {
                            view.showProgressBar(false)
                            view.enableButton(true)
                        }
                ))

    }

    fun unsubscribe() = disposable.clear()

}