package com.webhopers.medlog.login

import android.content.res.Resources
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.isEmpty
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.utils.isConnected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LoginPresenter(val view: LoginView,
                     val resources: Resources,
                     val disposable: CompositeDisposable = CompositeDisposable()) {

    fun onLogin() {
        if (!validInput()) return

        if (!isConnected(view.getContext())) {
            view.showSnackbar(R.string.no_internet)
            return
        }

        view.showProgressBar(true)
        view.enableButtons(false)

        val email = view.getEmailField().text.toString()
        val password = view.getPasswordField().text.toString()

        if (email ==  resources.getString(R.string.admin)
                && password == resources.getString(R.string.pass)) {
            view.startAdminMainActivity()
            return
        }

        signInUser(email, password)
    }

    private fun validInput(): Boolean {
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

    private fun signInUser(email: String, password: String) {
        disposable.add(FirebaseAuthService.signInUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            view.startMedRepActivity()
                        },
                        onError = {
                            view.enableButtons(true)
                            view.showProgressBar(false)
                        }))
    }

    fun createUserAccount() {
        view.startRegisterActivity()
    }

    fun unsubscribe() {
        disposable.clear()
    }
}
