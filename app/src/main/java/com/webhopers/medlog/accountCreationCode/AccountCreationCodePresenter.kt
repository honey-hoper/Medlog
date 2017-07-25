package com.webhopers.medlog.accountCreationCode

import com.webhopers.medlog.extensions.isEmpty
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AccountCreationCodePresenter (val view: AccountCreationCodeView,
                                    val disposable: CompositeDisposable = CompositeDisposable()) {

    init {
        getACC()
    }

    fun getACC() {
        view.showProgressBar(true)
        disposable.add(FirebaseDatabaseService.getACC()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {},
                        onComplete = {},
                        onNext = {
                            view.setCode(it)
                            view.showProgressBar(false)
                        }))
    }

    fun setACC() {
        val accField = view.getACCField()
        if (accField.isEmpty()) {
            view.getACCField().setError("Empty")
            return
        }

        val code = accField.text.toString().trim()

        if (code.length != 4) {
            accField.error = "Length must be 4"
            return
        }

        FirebaseDatabaseService.resetACC(code.toInt())
        accField.setText("")
    }

    fun unsubscribe() = disposable.clear()
}