package com.webhopers.medlog.adminMain

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.SelectableAdapter
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.services.storage.FirebaseStorageService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AdminMainPresenter(val context: Context,
                         val activity: AppCompatActivity,
                         val view: AdminMainView,
                         val resources: Resources,
                         val disposable: CompositeDisposable = CompositeDisposable()) {


    private val SESSION_FILE = "SESSION_FILE"
    private val ADMIN_SESSION = "ADMIN_SESSION"

    init {
        DataHolder.changeView(view)
    }

    fun signout() {
        FirebaseAuthService.signOutUser()
        view.startSplashActivity()
    }

    fun changeRecyclerViewAdapter(path: String, resources: Resources) {
        val ref = FirebaseDatabase.getInstance()
                .reference
                .child(path)


        view.showProgressBar(true)
        DataHolder.changePath(path)

        val adapter = SelectableAdapter(view, context, activity, resources, path, ref)
        view.getRecyclerView().adapter = adapter
        view.getDecorView().background = ContextCompat.getDrawable(context, R.color.colorAccent)

    }

    fun upload(uri: Uri, path: String) {
        view.setProgressDialogStyle(ProgressDialog.STYLE_HORIZONTAL, "Uploading", false)
        view.setProgressDialogProgress(0)
        view.showProgressDialog(true)

        disposable.add(FirebaseStorageService.uploadFile(uri, path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            view.makeToast("Uploaded Successfully")
                            view.showProgressDialog(false)
                        },
                        onError = {
                            view.makeToast("Could not upload")
                            view.showProgressDialog(false)
                        },
                        onNext = {i -> view.setProgressDialogProgress(i) }))
    }


    fun unsubscribe() {
        disposable.clear()
    }
}